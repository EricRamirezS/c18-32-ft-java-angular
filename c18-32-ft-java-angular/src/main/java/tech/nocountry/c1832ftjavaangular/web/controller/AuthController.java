package tech.nocountry.c1832ftjavaangular.web.controller;

import com.mailersend.sdk.Recipient;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.entity.UserDataEmbeddable;
import tech.nocountry.c1832ftjavaangular.model.auth.EmailValidationRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.ForgottenPasswordChangeRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.LoginRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.LoginResponse;
import tech.nocountry.c1832ftjavaangular.model.auth.PasswordChangeRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.PasswordRecoveryRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.RegisterRequest;
import tech.nocountry.c1832ftjavaangular.security.JwtIssuer;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;
import tech.nocountry.c1832ftjavaangular.service.EmailService;
import tech.nocountry.c1832ftjavaangular.service.UserAccountService;
import tech.nocountry.c1832ftjavaangular.utils.StringResponse;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest data) {
        try {
            var authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(),
                            data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            String token = jwtIssuer.issue(principal.getId(), principal.getUsername(), principal.getEmail());
            return ResponseEntity.ok(LoginResponse.builder().accessToken(token).build());
        } catch (AuthenticationException ex) {
            UserAccountEntity user = userAccountService.getUserAccountByEmail(data.getEmail()).orElseThrow(() -> ex);
            if (user.getActive()) throw ex;
            setTemporalToken(user);
            sendVerificationEMail(user, data.getEmailVerificationCallbackUrl(), user.getTempToken());

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not verified");
        }
    }

    @SecurityRequirement(name = "Authorization")
    @PutMapping("/change-password")
    public ResponseEntity<UserAccountEntity> passwordChange(@AuthenticationPrincipal UserPrincipal userData,
                                                            @RequestBody PasswordChangeRequest data) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail(),
                data.getOldPassword()));
        UserAccountEntity user =
                userAccountService.getUserAccountById(userData.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setPassword(passwordEncoder.encode(data.getNewPassword()));
        user = userAccountService.saveUser(user);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/change-forgotten-password")
    public ResponseEntity<UserAccountEntity> forgottenPasswordChange(@AuthenticationPrincipal UserPrincipal userData,
                                                                     @RequestBody ForgottenPasswordChangeRequest data) {
        UserAccountEntity user =
                userAccountService.getUserAccountByToken(data.getToken()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token"));
        if (user.getTokenExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token");
        }
        user.setPassword(passwordEncoder.encode(data.getNewPassword()));
        user.setTempToken(null);
        user.setTokenExpiryDate(null);
        user = userAccountService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserAccountEntity> register(@RequestBody RegisterRequest data) {
        if (userAccountService.existsUserAccountByUsername(data.getUsername()) || userAccountService.existsUserAccountByEmail(data.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User account already exists");
        }

        UserAccountEntity user =
                UserAccountEntity.builder().username(data.getUsername()).email(data.getEmail()).password(passwordEncoder.encode(data.getPassword())).userData(UserDataEmbeddable.builder().name(data.getUsername()).build()).active(false).build();


        setTemporalToken(user);

        if (sendVerificationEMail(user, data.getCallbackUrl(), user.getTempToken())) {
            user = userAccountService.saveUser(user);
            return ResponseEntity.created(URI.create(data.getRedirectUrl())).body(user);
        } else {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Email service Failed");
        }
    }

    @PostMapping("/recovery")
    public ResponseEntity<StringResponse> recoverPassword(@RequestBody PasswordRecoveryRequest data) {
        UserAccountEntity user =
                userAccountService.getUserAccountByEmail(data.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        setTemporalToken(user);

        if (sendResetPasswordEMail(user, data.getCallbackUrl(), user.getTempToken())) {
            userAccountService.saveUser(user);
            return ResponseEntity.created(URI.create(data.getRedirectUrl())).body(new StringResponse("Password reset " +
                                                                                                     "email sent " +
                                                                                                     "successful"));

        }
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Email service Failed");

    }

    @PostMapping("/email-verify")
    public ResponseEntity<LoginResponse> verifyEmail(@RequestBody EmailValidationRequest data) {
        UserAccountEntity user =
                userAccountService.getUserAccountByToken(data.getToken()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid token"));
        if (user.getActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already activated");
        }

        if (user.getTokenExpiryDate().isBefore(OffsetDateTime.now())) {
            setTemporalToken(user);
            if (sendVerificationEMail(user, data.getReactivateCallbackUrl(), user.getTempToken())) {
                userAccountService.saveUser(user);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired Token");
            } else {
                throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Email service Failed");
            }
        }

        user.setTempToken(null);
        user.setTokenExpiryDate(null);
        user.setActive(true);
        userAccountService.saveUser(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtIssuer.issue(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(LoginResponse.builder().accessToken(token).build());
    }

    private void setTemporalToken(@NotNull UserAccountEntity user) {
        user.setTempToken(UUID.randomUUID().toString());
        user.setTokenExpiryDate(Instant.now().plus(1, ChronoUnit.HOURS).atOffset(ZoneOffset.UTC));
    }

    private boolean sendVerificationEMail(@NotNull UserAccountEntity user, String callbackUrl, String token) {
        return emailService.sendPlainEmail(List.of(new Recipient(user.getUsername(), user.getEmail())), "Verify your " +
                                                                                                        "email",
                "Account created, please verify your email with the next URL: \n" + "<a href=\"" + callbackUrl + token + "\"> Activation link </a>\n", true);
    }

    private boolean sendResetPasswordEMail(@NotNull UserAccountEntity user, String callbackUrl, String token) {
        return emailService.sendPlainEmail(List.of(new Recipient(user.getUsername(), user.getEmail())), "Reset " +
                                                                                                        "password",
                "To change you password, please follow the next link: \n" + "<a href=\"" + callbackUrl + token + "\">" +
                " Reset link </a>\n", true);
    }
}
