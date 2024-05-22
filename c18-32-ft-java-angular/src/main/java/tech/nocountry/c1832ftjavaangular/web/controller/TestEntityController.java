package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.TestEntity;
import tech.nocountry.c1832ftjavaangular.model.StringResponse;
import tech.nocountry.c1832ftjavaangular.repository.TestEntityRepository;

@Tag(name = "Test Methods", description = "Methods provides as an example and to test database")
@RestController
@RequestMapping("/test")
public class TestEntityController {

    private final TestEntityRepository testEntityRepository;
    private final ModelMapper modelMapper;

    public TestEntityController(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Operation(summary = "Get a list of test values", description = "Get a list of values from the database. For " +
                                                                    "testing purposes.")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
    @Schema(implementation = TestEntity.class))})
    @GetMapping
    public ResponseEntity<Iterable<TestEntity>> findAllValues() {
        return ResponseEntity.ok(testEntityRepository.findAll());
    }
    @Operation(summary = "Quick Test", description = "Get a list of values from the database. For " +
                                                                    "testing purposes.")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
    @Schema(implementation = TestEntity.class))})
    @GetMapping
    public ResponseEntity<Iterable<TestEntity>> quickTest() {
        return ResponseEntity.ok(testEntityRepository.findAll());
    }

    @Operation(summary = "Get a test value", description = "Get a value from the database. For testing purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = TestEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TestEntity> findValue(@PathVariable Integer id) {
        TestEntity entity = testEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "Update a test value", description = "Update a value from the database. For testing purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = TestEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @PutMapping
    public ResponseEntity<TestEntity> updateValue(@RequestBody TestEntity testEntity) {
        if (!testEntityRepository.existsById(testEntity.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        return ResponseEntity.ok(testEntityRepository.save(testEntity));
    }

    @Operation(summary = "Update a test value", description = "Update a value from the database. For testing purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = TestEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @PatchMapping
    public ResponseEntity<TestEntity> patchValue(@RequestBody TestEntity testEntity) {
        TestEntity entity = testEntityRepository.findById(testEntity.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
        modelMapper.map(testEntity, entity);
        return ResponseEntity.ok(testEntityRepository.save(entity));
    }

    @Operation(summary = "Delete a test value", description = "Delete a value from the database. For testing purposes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = StringResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<StringResponse> deleteValue(@PathVariable Integer id) {
        TestEntity entity = testEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
        testEntityRepository.delete(entity);
        return ResponseEntity.ok(new StringResponse("Deleted value with id: %s", id));
    }

    @Operation(summary = "Add a test value", description = "Add a value to the database. For testing purposes.")
    @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema =
    @Schema(implementation = TestEntity.class))})
    @PostMapping
    public ResponseEntity<TestEntity> addOneValue(@RequestBody TestEntity entity) {
        return new ResponseEntity<>(testEntityRepository.save(entity), HttpStatus.CREATED);
    }
}