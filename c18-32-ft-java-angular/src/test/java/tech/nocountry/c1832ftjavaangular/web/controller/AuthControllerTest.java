package tech.nocountry.c1832ftjavaangular.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.model.auth.EmailValidationRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.ForgottenPasswordChangeRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.LoginRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.PasswordChangeRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.PasswordRecoveryRequest;
import tech.nocountry.c1832ftjavaangular.model.auth.RegisterRequest;
import tech.nocountry.c1832ftjavaangular.security.JwtIssuer;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;
import tech.nocountry.c1832ftjavaangular.service.EmailService;
import tech.nocountry.c1832ftjavaangular.service.UserAccountService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtIssuer jwtIssuer;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private EmailService emailService;

    private UserPrincipal userPrincipal;
    private UserAccountEntity user;

    @BeforeEach
    void setUp() {
        userPrincipal = UserPrincipal.builder()
                .id(1L)
                .username("username")
                .email("test@example.com")
                .password("Password123")
                .isAccountEnabled(true)
                .build();
        user = UserAccountEntity.builder()
                .username("username")
                .email("test@example.com")
                .password("Password123")
                .active(true).build();
        user.setId(1L);
        when(userAccountService.getUserAccountById(1L)).thenReturn(Optional.of(user));
        when(userAccountService.getUserAccountByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userAccountService.getUserAccountByUsername("username")).thenReturn(Optional.of(user));
        when(userAccountService.getUserAccountByUsername("fakeUsername")).thenReturn(Optional.empty());
        when(userAccountService.existsUserAccountByEmail("test@example.com")).thenReturn(true);
        when(userAccountService.existsUserAccountByEmail("fake@example.com")).thenReturn(false);
        when(userAccountService.existsUserAccountByUsername("username")).thenReturn(true);
        when(userAccountService.existsUserAccountByUsername("fake")).thenReturn(false);
        when(userAccountService.getUserAccountByToken("validToken")).thenReturn(Optional.of(user));
        when(userAccountService.getUserAccountByToken("invalidToken")).thenReturn(Optional.empty());
    }

    @Test
    void loginSuccessful() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("Password123")
                .emailVerificationCallbackUrl("http://test.url/").build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", matchesRegex("(^[\\w-]*\\.[\\w-]*\\.[\\w-]*$)")));
    }

    @Test
    void loginUserNotVerified() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("Password123")
                .emailVerificationCallbackUrl("SomeUrl").build();

        user.setActive(false);
        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Bad credentials") {
        });

        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isForbidden());
    }

    @Test
    void passwordChangeSuccessful() throws Exception {
        // Arrange
        PasswordChangeRequest changeRequest = new PasswordChangeRequest("oldPassword", "newPassword");
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userAccountService.saveUser(any(UserAccountEntity.class))).thenReturn(user);

        String token = jwtIssuer.issue(1L, "username", "test@example.com");
        ResultActions result = mockMvc.perform(put("/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("username")));
    }

    @Test
    void forgottenPasswordChangeInvalidToken() throws Exception {
        ForgottenPasswordChangeRequest request = new ForgottenPasswordChangeRequest("invalidToken", "newPassword");

        when(userAccountService.getUserAccountByToken(anyString())).thenReturn(Optional.empty());

        ResultActions result = mockMvc.perform(put("/auth/change-forgotten-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void forgottenPasswordChangeTokenExpired() throws Exception {
        ForgottenPasswordChangeRequest request = new ForgottenPasswordChangeRequest("validToken", "newPassword");

        user.setTokenExpiryDate(OffsetDateTime.now().minusHours(1));

        ResultActions result = mockMvc.perform(put("/auth/change-forgotten-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void registerUserAlreadyExists() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "test@example.com", "password",
                "callbackUrl", "redirectUrl");

        when(userAccountService.existsUserAccountByUsername(anyString())).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        result.andExpect(status().isConflict());
    }

    @Test
    void registerEmailAlreadyExists() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "test@example.com", "password",
                "callbackUrl", "redirectUrl");

        ResultActions result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        result.andExpect(status().isConflict());
    }

    @Test
    void recoverPassword_userNotFound() throws Exception {
        PasswordRecoveryRequest recoveryRequest = new PasswordRecoveryRequest("fake@example.com", "callbackUrl",
                "redirectUrl");

        ResultActions result = mockMvc.perform(post("/auth/recovery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recoveryRequest)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void recoverPasswordEmailServiceFailure() throws Exception {
        PasswordRecoveryRequest recoveryRequest = new PasswordRecoveryRequest("test@example.com", "callbackUrl",
                "redirectUrl");

        when(emailService.sendPlainEmail(anyList(), anyString(), anyString(), anyBoolean())).thenReturn(false);

        ResultActions result = mockMvc.perform(post("/auth/recovery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recoveryRequest)));

        result.andExpect(status().isFailedDependency());
    }

    @Test
    void verifyEmailInvalidToken() throws Exception {
        EmailValidationRequest validationRequest = new EmailValidationRequest("invalidToken", "reactivateCallbackUrl");

        ResultActions result = mockMvc.perform(post("/auth/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validationRequest)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void verifyEmailTokenExpired() throws Exception {
        EmailValidationRequest validationRequest = new EmailValidationRequest("validToken", "reactivateCallbackUrl");

        user.setTokenExpiryDate(OffsetDateTime.now().minusHours(1));
        user.setActive(false);
        when(emailService.sendPlainEmail(anyList(), anyString(), anyString(), anyBoolean())).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/auth/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validationRequest)));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void verifyEmailAlreadyActivated() throws Exception {
        EmailValidationRequest validationRequest = new EmailValidationRequest("validToken", "reactivateCallbackUrl");

        ResultActions result = mockMvc.perform(post("/auth/email-verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validationRequest)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void loginInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongPassword", "someUrl");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {
                });

        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void passwordChange_oldPasswordIncorrect() throws Exception {
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("wrongOldPassword", "newPassword");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new AuthenticationException("Old password incorrect") {
                });

        String token = jwtIssuer.issue(1L, "username", "test@example.com");

        ResultActions result = mockMvc.perform(put("/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(objectMapper.writeValueAsString(passwordChangeRequest)));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void passwordChangeUserNotFound() throws Exception {
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("oldPassword", "newPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        String token = jwtIssuer.issue(2L, "fakeUsername", "fake@example.com");

        ResultActions result = mockMvc.perform(put("/auth/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(objectMapper.writeValueAsString(passwordChangeRequest)));

        result.andExpect(status().isNotFound());
    }
}