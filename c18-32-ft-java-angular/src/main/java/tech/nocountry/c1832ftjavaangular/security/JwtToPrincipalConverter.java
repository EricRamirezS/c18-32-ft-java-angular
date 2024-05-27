package tech.nocountry.c1832ftjavaangular.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {

    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .id(Long.parseLong(jwt.getSubject()))
                .username(jwt.getClaim("username").asString())
                .email(jwt.getClaim("email").asString())
                .isAccountEnabled(jwt.getClaim("enabled").asBoolean())
                .authorities(getAuthorities(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> getAuthorities(@NotNull DecodedJWT jwt) {
        Claim claim = jwt.getClaim("authorities");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
