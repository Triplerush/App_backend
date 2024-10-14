package com.example.demo.dao.recommendation;

import com.example.demo.dao.stratum.Stratum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "recommendations")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Solo incluir campos explícitos en equals/hashCode
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recommendation")
    @EqualsAndHashCode.Include // Incluir este campo en equals y hashCode
    private Long idRecommendation;

    @Column(nullable = false)
    private String content;

    private boolean active = true;

    @ManyToMany(mappedBy = "recommendations")
    @JsonIgnore
    private Set<Stratum> strata;

    // La colección strata se excluye de equals y hashCode
}
