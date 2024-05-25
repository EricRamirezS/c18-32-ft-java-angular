package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataEntity extends BaseAuditEntity {
    private String name;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime birthday;
    @Column(columnDefinition = "TEXT")
    private String profilePicture;
    @ManyToOne
    private CountryEntity country;
    @NotNull
    @OneToOne
    private UserAccountEntity userAccount;

    private Geometry location;
}
