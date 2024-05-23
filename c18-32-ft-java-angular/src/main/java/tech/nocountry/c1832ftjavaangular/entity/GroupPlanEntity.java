package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "group_plan")
public class GroupPlanEntity extends BaseAuditEntity {

    @NotNull
    private String name;
    @NotNull
    @ManyToOne
    private UserAccountEntity owner;

    @OneToMany
    private List<GroupPlanMemberEntity> members;
}
