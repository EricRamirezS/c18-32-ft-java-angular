package tech.nocountry.c1832ftjavaangular.web.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.nocountry.c1832ftjavaangular.entity.EventEntity;
import tech.nocountry.c1832ftjavaangular.entity.VoteEntity;
import tech.nocountry.c1832ftjavaangular.model.event.EventCreateRequest;
import tech.nocountry.c1832ftjavaangular.model.event.EventUpdateRequest;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/events")
public class EventController {

    @GetMapping
    public ResponseEntity<Iterable<EventEntity>> getEvents(@RequestBody long groupPlanId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");

    }

    @GetMapping("/{id}")
    public ResponseEntity<EventEntity> getEvent(@PathVariable long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");

    }

    @PostMapping
    public ResponseEntity<EventEntity> createEvent(@RequestBody EventCreateRequest data) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @PutMapping
    public ResponseEntity<EventEntity> updateEvent(@RequestBody EventUpdateRequest data) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @GetMapping("votes")
    public ResponseEntity<Iterable<VoteEntity>> getAllVoter(@RequestParam long eventId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }
}
