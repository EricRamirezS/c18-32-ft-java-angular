package tech.nocountry.c1832ftjavaangular.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
public class EmailValidationRequest {
    private String token;
    @URL
    private String reactivateCallbackUrl;
}
