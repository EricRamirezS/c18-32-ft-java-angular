package tech.nocountry.c1832ftjavaangular.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;
import tech.nocountry.c1832ftjavaangular.repository.UserAccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

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
}
