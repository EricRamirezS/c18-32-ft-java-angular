package tech.nocountry.c1832ftjavaangular.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseAuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonIgnore
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonIgnore
    private OffsetDateTime updatedAt;

}
