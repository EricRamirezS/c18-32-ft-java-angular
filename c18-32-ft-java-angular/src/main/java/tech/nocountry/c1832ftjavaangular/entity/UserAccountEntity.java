package tech.nocountry.c1832ftjavaangular.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collection;

import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Column(name = "password_hash")
    private String password; // Almacena un hash de contraseña en lugar de una contraseña en texto plano

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
    @Column(name = "temp_token", length = 36)
    private String tempToken; // Almacenar token de restablecimiento de contraseña en un campo de tipo String

    @JsonIgnore
    @Column(name = "token_expiry_date")
    private OffsetDateTime tokenExpiryDate;

    @JsonIgnore
    @Column(name = "activation_date")
    private OffsetDateTime activationDate;

    @JsonIgnore
    @Transient // No se almacena en la base de datos 
    private PasswordEncoder passwordEncoder;

    // Metodo para establecer la contraseña (ahora con hashing)
    public void setPassword(String password) {
        if (password != null) {
            this.password = passwordEncoder.encode(password); // Almacena directamente el hash de contraseña
        }
    }

    // Metodo para verificar la contraseña
    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
