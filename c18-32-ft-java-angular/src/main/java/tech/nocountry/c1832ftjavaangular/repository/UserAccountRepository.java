package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.nocountry.c1832ftjavaangular.entity.UserAccountEntity;

import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUsernameIgnoreCase(String username);

    Optional<UserAccountEntity> findByEmailIgnoreCase(String email);

    Optional<UserAccountEntity> findByTempToken(String activationToken);

    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
}
