package tech.nocountry.c1832ftjavaangular.model.auth;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    @Min(8)
    @Max(64)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", flags = Pattern.Flag.UNICODE_CASE)
    private String password;
    @URL
    private String callbackUrl;
    @URL
    private String redirectUrl;
}
