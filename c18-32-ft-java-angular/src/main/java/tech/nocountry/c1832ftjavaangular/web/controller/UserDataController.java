package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.nocountry.c1832ftjavaangular.entity.UserDataEmbeddable;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;
import tech.nocountry.c1832ftjavaangular.service.UserAccountService;

@SecurityRequirement(name = "Authorization")
@RestController()
@RequestMapping("user")
@AllArgsConstructor
public class UserDataController {

    private final UserAccountService userAccountService;

    @GetMapping
    public ResponseEntity<UserDataEmbeddable> getUserData(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userAccountService.getUserAccountById(user.getId())
                .orElseThrow()
                .getUserData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataEmbeddable> getUserData(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountService.getUserAccountById(id)
                .orElseThrow()
                .getUserData());
    }
}
