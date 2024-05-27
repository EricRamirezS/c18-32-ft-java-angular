package tech.nocountry.c1832ftjavaangular.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
@Builder
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    @URL
    private String emailVerificationCallbackUrl;
}
