package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;
import tech.nocountry.c1832ftjavaangular.model.EventType;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime fromDateTime;
    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime toDateTime;
    @NotNull
    @ManyToOne
    private GroupPlanEntity groupPlan;
    @ManyToOne
    private PlaceEntity placeEntity;

    private Geometry startingLocation;
    private Geometry endingLocation;

    @OneToMany
    private List<VoteEntity> Votes;
    @OneToMany
    private List<GroupPlanCommentEntity> Comments;

}
