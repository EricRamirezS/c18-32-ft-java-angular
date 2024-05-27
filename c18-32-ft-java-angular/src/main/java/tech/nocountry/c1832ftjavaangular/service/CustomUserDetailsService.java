package tech.nocountry.c1832ftjavaangular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccountEntity user = userAccountService.getUserAccountByEmail(email).orElseThrow();
        return loadUserByEntity(user);
    }

    public UserDetails loadUserByEntity(UserAccountEntity user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority("User")))
                .password(user.getPassword())
                .isAccountEnabled(user.getActive())
                .build();
    }
}
