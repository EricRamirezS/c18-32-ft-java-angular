package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tech.nocountry.c1832ftjavaangular.model.VoteType;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class VoteEntity extends BaseAuditEntity {

    @NotNull
    private VoteType voteType;
    @NotNull
    @ManyToOne
    private GroupPlanMemberEntity groupPlanMember;
    @NotNull
    @ManyToOne
    private EventEntity event;
}