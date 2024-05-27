package tech.nocountry.c1832ftjavaangular.model.event;

import tech.nocountry.c1832ftjavaangular.utils.LocationInfo;

import java.time.OffsetDateTime;

public class EventUpdateRequest {
    private EventType eventType;
    private OffsetDateTime fromDateTime;
    private OffsetDateTime toDateTime;
    private long placeEntityId;

    private LocationInfo startingLocation;
    private LocationInfo endingLocation;
}
