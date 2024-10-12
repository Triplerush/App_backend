package com.example.demo.dao.stratum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "strata")
@NoArgsConstructor
@AllArgsConstructor
public class Stratum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stratum")
    private Long idStratum;

    @Column(name = "stratum_name")
    private String stratumName;

    @Column(name = "description")
    private String description;

    private boolean active = true;
}
