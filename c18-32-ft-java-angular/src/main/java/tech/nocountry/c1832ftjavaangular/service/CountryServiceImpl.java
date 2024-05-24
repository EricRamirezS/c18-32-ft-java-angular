package tech.nocountry.c1832ftjavaangular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;
import tech.nocountry.c1832ftjavaangular.repository.CountryRepository;

import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    @Autowired
    public CountryServiceImpl(CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<CountryEntity> getAllCountries() {
        return null;
    }

    @Override
    public Optional<CountryEntity> getCountry(int id) {
        return repository.findById(id);
    }
}
