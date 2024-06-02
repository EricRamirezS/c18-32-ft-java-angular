package tech.nocountry.c1832ftjavaangular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.repository.UserAccountRepository;
import tech.nocountry.c1832ftjavaangular.security.UserPrincipal;

import java.util.List;

@Component
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository; // Inyecta el repositorio directamente

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca el usuario por email en el repositorio (eliminando la dependencia de UserAccountService)
        UserAccountEntity user = userAccountRepository.findByEmailIgnoreCase(email).orElseThrow();
        return loadUserByEntity(user); // Convierte la entidad UserAccountEntity en un objeto UserDetails
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
