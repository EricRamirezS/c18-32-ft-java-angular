package tech.nocountry.c1832ftjavaangular.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;
import tech.nocountry.c1832ftjavaangular.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CountryServiceTest {

    @Autowired
    private CountryService countryService;
    @MockBean
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        CountryEntity country = CountryEntity.builder()
                .id(56)
                .iso("CL")
                .name("CHILE")
                .niceName("Chile")
                .iso3("CHL")
                .numericCode(152)
                .phoneCode(56)
                .build();
        List<CountryEntity> data = List.of(country);
        Mockito.when(countryRepository.findById(56)).thenReturn(Optional.of(country));
        Mockito.when(countryRepository.findAll()).thenReturn(data);
    }

    @Test
    public void getAllCountries() {
        Iterable<CountryEntity> allCountries = countryService.getAllCountries();
        Assert.notEmpty(List.of(allCountries), "Should have at least one Country");
    }

    @Test
    public void findByIdFound() {
        Optional<CountryEntity> allCountries = countryService.getCountry(56);
        Assert.isTrue(allCountries.isPresent(), "Should found data");
        assertEquals("CHILE", allCountries.get().name);
    }

}