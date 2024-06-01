package tech.nocountry.c1832ftjavaangular.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.exception.InvalidTokenException;
import tech.nocountry.c1832ftjavaangular.repository.UserAccountRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserAccountEntity> getUserAccountByUsername(String username) {
        return userAccountRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public Optional<UserAccountEntity> getUserAccountByEmail(String email) {
        return userAccountRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<UserAccountEntity> getUserAccountById(Long id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public Optional<UserAccountEntity> getUserAccountByToken(String token) {
        return userAccountRepository.findByTempToken(token);
    }

    @Override
    public UserAccountEntity saveUser(UserAccountEntity user) {
        return userAccountRepository.save(user);
    }

    @Override
    public Boolean existsUserAccountByUsername(String username) {
        return userAccountRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public Boolean existsUserAccountByEmail(String email) {
        return userAccountRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Optional<UserAccountEntity> optionalUser = getUserAccountByToken(token); // Revisa si el token es válido

        if (optionalUser.isPresent()) { // Verifica si el usuario existe
            UserAccountEntity user = optionalUser.get(); // Extrae el usuario del Optional

            if (user.getTokenExpiryDate().isBefore(OffsetDateTime.now())) {
                throw new InvalidTokenException("Token has expired"); // Lanza una excepcion personalizada
            }
            
            //  Hashear la nueva contraseña antes de guardarla
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);

            user.setTempToken(null);
            user.setTokenExpiryDate(null);
            userAccountRepository.save(user);
        } else {
            throw new InvalidTokenException("Invalid token"); // Manejo de token invalido y lanza una excepcion personalizada
        }
    }
}
