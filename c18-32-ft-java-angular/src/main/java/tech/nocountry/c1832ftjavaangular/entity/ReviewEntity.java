package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseAuditEntity {
    @NotNull
    @ManyToOne
    private UserAccountEntity userAccount;
    @NotNull
    private String comment;
    @Min(1)
    @Max(5)
    @NotNull
    private Integer rating;
    @NotNull
    @ManyToOne
    private PlaceEntity place;
}
