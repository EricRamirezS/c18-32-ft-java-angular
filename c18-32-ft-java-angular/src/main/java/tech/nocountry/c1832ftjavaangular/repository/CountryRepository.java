package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;

import java.util.Optional;

@SuppressWarnings("unused")
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    Optional<CountryEntity> findByNameIgnoreCase(String string);
}
