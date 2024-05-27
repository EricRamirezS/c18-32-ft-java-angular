package tech.nocountry.c1832ftjavaangular.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgottenPasswordChangeRequest {
    private String token;
    private String newPassword;
}
