package tech.nocountry.c1832ftjavaangular.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;

import java.util.Optional;

@DataJpaTest
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TestEntityManager entityManager;
    private int chileId = 0;

    @BeforeEach
    void setUp() {
        CountryEntity country = CountryEntity.builder()
                .iso("CL")
                .name("CHILE")
                .niceName("Chile")
                .iso3("CHL")
                .numericCode(152)
                .phoneCode(6)
                .build();
        chileId = entityManager.persistAndGetId(country, Integer.class);
    }

    @Test
    public void FindByIdFound() {
        Optional<CountryEntity> country = countryRepository.findById(chileId);
        Assert.isTrue(country.isPresent());
        Assert.equals(country.get().name, "CHILE");
    }

    @Test
    public void FindByIdNotFound() {
        Optional<CountryEntity> country = countryRepository.findById(-1);
        Assert.isTrue(country.isEmpty());
    }
    @Test
    public void FindByNameIgnoreCaseFound() {
        Optional<CountryEntity> country = countryRepository.findByNameIgnoreCase("Chile");
        Assert.isTrue(country.isPresent());
        Assert.equals(country.get().name, "CHILE");
    }

    @Test
    public void FindByIdNameIgnoreCaseNotFound() {
        Optional<CountryEntity> country = countryRepository.findByNameIgnoreCase("CHIEL");
        Assert.isTrue(country.isEmpty());
    }
}