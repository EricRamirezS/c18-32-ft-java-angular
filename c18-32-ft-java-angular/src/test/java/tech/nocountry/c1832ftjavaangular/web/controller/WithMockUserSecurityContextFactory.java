package tech.nocountry.c1832ftjavaangular.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var principal = UserPrincipal.builder()
                .id(annotation.id())
                .username("Test User")
                .email("test@test.com")
                .isAccountEnabled(true)
                .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
        return context;
    }
}