package com.example.demo.dao.stratum;

import com.example.demo.dao.recommendation.Recommendation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "strata")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Stratum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stratum")
    @EqualsAndHashCode.Include
    private Long idStratum;

    @Column(name = "stratum_name", nullable = false)
    private String stratumName;

    private String description;
    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "strata_recommendations",
            joinColumns = @JoinColumn(name = "id_stratum"),
            inverseJoinColumns = @JoinColumn(name = "id_recommendation")
    )
    private Set<Recommendation> recommendations;

}
