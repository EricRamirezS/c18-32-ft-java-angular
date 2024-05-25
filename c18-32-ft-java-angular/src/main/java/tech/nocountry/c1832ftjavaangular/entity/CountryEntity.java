package tech.nocountry.c1832ftjavaangular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "country")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    public String iso;
    @NotNull
    public String name;
    @NotNull
    public String niceName;
    public String iso3;
    public Integer numericCode;
    @NotNull
    public Integer phoneCode;
}
