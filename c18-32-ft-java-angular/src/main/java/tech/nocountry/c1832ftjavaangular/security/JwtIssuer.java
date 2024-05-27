package tech.nocountry.c1832ftjavaangular.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.nocountry.c1832ftjavaangular.properties.JwtProperties;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;

    public String issue(long id, String username, String email) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.ofDays(1)))
                .withClaim("username", username)
                .withClaim("email", email)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));

    }
}
