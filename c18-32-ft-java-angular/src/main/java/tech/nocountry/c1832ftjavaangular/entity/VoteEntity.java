package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.nocountry.c1832ftjavaangular.model.vote.VoteType;

@Getter
@Setter
@Entity
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
