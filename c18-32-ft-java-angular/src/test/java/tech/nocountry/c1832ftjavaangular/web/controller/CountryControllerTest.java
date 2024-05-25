package tech.nocountry.c1832ftjavaangular.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;
import tech.nocountry.c1832ftjavaangular.service.CountryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unchecked")
@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService countryService;

    private final List<CountryEntity> countryEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        countryEntities.add(CountryEntity.builder()
                .id(43)
                .iso("CL")
                .name("CHILE")
                .niceName("Chile")
                .iso3("CHL")
                .numericCode(152)
                .phoneCode(56)
                .build());
        countryEntities.add(CountryEntity.builder()
                .id(44)
                .iso("CN")
                .name("CHINA")
                .niceName("China")
                .iso3("CHN")
                .numericCode(156)
                .phoneCode(86)
                .build());
        countryEntities.add(CountryEntity.builder()
                .id(206)
                .iso("CH")
                .name("SWITZERLAND")
                .niceName("Switzerland")
                .iso3("CHE")
                .numericCode(756)
                .phoneCode(41)
                .build());
        countryEntities.add(CountryEntity.builder()
                .id(208)
                .iso("TW")
                .name("TAIWAN, PROVINCE OF CHINA")
                .niceName("Taiwan, Province of China")
                .iso3("TWN")
                .numericCode(158)
                .phoneCode(886)
                .build());
    }

    @Test
    public void getAllCountries() throws Exception {
        Mockito.when(countryService.findAll(any(Example.class)))
                .thenReturn(countryEntities);
        mvc.perform(get("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].iso").value("CL"))
                .andExpect(jsonPath("$[1].iso").value("CN"))
                .andExpect(jsonPath("$[2].iso").value("CH"))
                .andExpect(jsonPath("$[3].iso").value("TW"));
    }

    @Test
    public void getAllCountriesWhereNameContainsCH() throws Exception {
        Mockito.when(countryService.findAll(any(Example.class)))
                .thenReturn(List.of(countryEntities.get(0), countryEntities.get(1), countryEntities.get(3)));
        mvc.perform(get("/countries?name={name}", "CH")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].iso").value("CL"))
                .andExpect(jsonPath("$[1].iso").value("CN"))
                .andExpect(jsonPath("$[2].iso").value("TW"));
    }


    @Test
    public void getCountryById() throws Exception {
        Mockito.when(countryService.getCountry(43))
                .thenReturn(countryEntities.stream().findFirst().filter(countryEntity -> countryEntity.getId() == 43));
        mvc.perform(get("/countries/{id}", 43).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CHILE"));
    }
}