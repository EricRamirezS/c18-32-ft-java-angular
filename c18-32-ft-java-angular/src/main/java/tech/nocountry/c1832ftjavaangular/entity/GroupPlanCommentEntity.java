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

@Getter
@Setter
@Entity
@Table(name = "group_plan_comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPlanCommentEntity extends BaseAuditEntity {
    @NotNull
    private String message;
    @NotNull
    @ManyToOne
    private GroupPlanMemberEntity groupPlanMember;
}
