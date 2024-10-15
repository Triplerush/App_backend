package com.example.demo.dao.medication;

import com.example.demo.dao.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "medications")
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medication")
    private Long idMedication;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(name = "medication_name")
    private String medicationName;
    private String dosage;
    private String frequency;

    @Column(name = "treatment_start")
    private Date treatmentStart;

    @Column(name = "treatment_end")
    private Date treatmentEnd;

    private boolean active = true;
}
