package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "user_account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountEntity extends BaseAuditEntity {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    @OneToOne
    private UserDataEntity userData;
    @NotNull
    @OneToMany
    private Collection<GroupPlanMemberEntity> member;
}
