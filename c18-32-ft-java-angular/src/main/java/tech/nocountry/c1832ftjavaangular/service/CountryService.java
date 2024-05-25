package tech.nocountry.c1832ftjavaangular.service;

import org.springframework.data.domain.Example;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;

import java.util.Optional;

public interface CountryService {

    Iterable<CountryEntity> getAllCountries();

    Optional<CountryEntity> getCountry(int id);

    Iterable<CountryEntity> findAll(Example<CountryEntity> example);
}
