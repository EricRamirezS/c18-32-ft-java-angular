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
import tech.nocountry.c1832ftjavaangular.model.InviteStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "group_plan_member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPlanMemberEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;
    private String inviteToken;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expirationDate;

    @ManyToOne
    private UserAccountEntity userAccount;
    @NotNull
    @ManyToOne
    private GroupPlanEntity groupPlan;
    @OneToMany
    private List<GroupPlanCommentEntity> groupPlanComments;
}
