package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "group_plan_comment")
public class GroupPlanCommentEntity extends BaseAuditEntity {
    @NotNull
    private String message;
    @NotNull
    @ManyToOne
    private GroupPlanMemberEntity groupPlanMember;
}
