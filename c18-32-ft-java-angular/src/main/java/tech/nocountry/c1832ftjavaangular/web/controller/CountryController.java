package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;
import tech.nocountry.c1832ftjavaangular.model.ErrorResponse;
import tech.nocountry.c1832ftjavaangular.service.CountryService;

import java.util.Optional;

@Tag(name = "Countries", description = "Get information about countries.")
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService service;
    private final ExampleMatcher allMatcher;
    private final ExampleMatcher anyMatcher;

    @Autowired
    protected CountryController(CountryService service) {
        this.service = service;
        allMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
        anyMatcher = ExampleMatcher.matchingAny()
                .withMatcher("iso", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("niceName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("iso3", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("numericCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("phoneCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnoreNullValues();
    }

    @Operation(summary = "Get a list of values", description = "Get a list of values")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema =
            @Schema(implementation = CountryEntity.class)))),
    })

    @GetMapping
    public ResponseEntity<Iterable<CountryEntity>> getAll(
            @RequestParam Optional<String> iso,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> niceName,
            @RequestParam Optional<String> iso3,
            @RequestParam Optional<Integer> numericCode,
            @RequestParam Optional<Integer> phoneCode,
            Optional<Boolean> matchAll) {
        CountryEntity sample = CountryEntity.builder()
                .iso(iso.orElse(null))
                .name(name.orElse(null))
                .niceName(niceName.orElse(null))
                .iso3(iso3.orElse(null))
                .numericCode(numericCode.orElse(null))
                .phoneCode(phoneCode.orElse(null))
                .build();

        ExampleMatcher matcher = matchAll.isPresent() && matchAll.get() ? allMatcher : anyMatcher;

        Example<CountryEntity> example = Example.of(sample, matcher);

        return ResponseEntity.ok(service.findAll(example));
    }

    @Operation(summary = "Get a value by Id.", description = "Get a specific value by its Id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = CountryEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content(mediaType =
                    "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CountryEntity> find(@PathVariable Integer id) {
        CountryEntity entity = service.getCountry(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
        return ResponseEntity.ok(entity);
    }
}
