package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/test/auth")
public class AuthVerificationTestSampleController {

    @GetMapping("/required")
    public ResponseEntity<Boolean> getWithLoggedInRequired() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/not-required")
    public ResponseEntity<Boolean> getWithLoggedInNotRequired() {
        return ResponseEntity.ok(true);
    }
}
