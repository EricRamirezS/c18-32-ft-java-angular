package tech.nocountry.c1832ftjavaangular.service;


import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;

import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccountEntity> getUserAccountByUsername(String username);

    Optional<UserAccountEntity> getUserAccountByEmail(String email);

    Optional<UserAccountEntity> getUserAccountById(Long id);

    Optional<UserAccountEntity> getUserAccountByToken(String token);

    UserAccountEntity saveUser(UserAccountEntity user);

    Boolean existsUserAccountByUsername(String username);

    Boolean existsUserAccountByEmail(String email);

    void resetPassword(String token, String newPassword); // Nuevo metodo en la interfaz

}
