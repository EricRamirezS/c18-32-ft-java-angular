package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

import java.time.OffsetDateTime;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataEmbeddable {
    private String name;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime birthday;
    @Column(columnDefinition = "TEXT")
    private String profilePicture;
    @ManyToOne
    private CountryEntity country;

    private Geometry location;
}
