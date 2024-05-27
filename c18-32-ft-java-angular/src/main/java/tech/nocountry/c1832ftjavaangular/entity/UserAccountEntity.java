package tech.nocountry.c1832ftjavaangular.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
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
    @Column(unique = true)
    private String username;
    @JsonIgnore
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Embedded
    private UserDataEmbeddable userData;
    @OneToMany
    private Collection<GroupPlanMemberEntity> member;
    @NotNull
    private Boolean active;

    @JsonIgnore
    private String tempToken;
    @JsonIgnore
    private OffsetDateTime tokenExpiryDate;
    @JsonIgnore
    private OffsetDateTime activationDate;
}
