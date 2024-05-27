package tech.nocountry.c1832ftjavaangular.model.event;

import jakarta.validation.constraints.NotNull;
import tech.nocountry.c1832ftjavaangular.utils.LocationInfo;

import java.time.OffsetDateTime;

public class EventCreateRequest {
    @NotNull
    private EventType eventType;
    @NotNull
    private OffsetDateTime fromDateTime;
    @NotNull
    private OffsetDateTime toDateTime;
    @NotNull
    private long groupPlanId;
    @NotNull
    private long placeEntityId;

    private LocationInfo startingLocation;
    private LocationInfo endingLocation;
}
