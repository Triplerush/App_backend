package com.example.demo.dao.measurement;

import com.example.demo.dao.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "measurements")
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_measurement")
    private Long idMeasurement;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(name = "measurement_date")
    private Date measurementDate;

    @Column(name = "systolic_pressure")
    private int systolicPressure;
    @Column(name = "diastolic_pressure")
    private int diastolicPressure;
    @Column(name = "heart_rate")
    private int heartRate;

    private String notes;
    private boolean active = true;
}
