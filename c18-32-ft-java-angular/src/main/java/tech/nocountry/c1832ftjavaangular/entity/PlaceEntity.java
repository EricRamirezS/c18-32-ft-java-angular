package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "place")
public class PlaceEntity extends BaseAuditEntity {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Geometry location;
    @NotNull
    @ManyToOne
    private CountryEntity country;

    @OneToMany
    private List<ReviewEntity> reviews;

}
