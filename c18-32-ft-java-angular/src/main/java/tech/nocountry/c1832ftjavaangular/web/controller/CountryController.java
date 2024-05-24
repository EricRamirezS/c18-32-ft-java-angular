package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.CountryEntity;
import tech.nocountry.c1832ftjavaangular.model.ErrorResponse;
import tech.nocountry.c1832ftjavaangular.service.CountryService;

@Tag(name = "Countries", description = "Methods provides as an example and to test database")
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService service;

    @Autowired
    protected CountryController(CountryService service) {
        this.service = service;
    }

    @Operation(summary = "Get a list of values", description = "Get a list of values")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema =
            @Schema(implementation = CountryEntity.class)))),
    })
    @GetMapping
    public ResponseEntity<Iterable<CountryEntity>> getAll() {
        return ResponseEntity.ok(service.getAllCountries());
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
