// Patient.java
package com.example.demo.dao.patient;

import com.example.demo.dao.doctor.Doctor;
import com.example.demo.dao.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long idPatient;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    private int age;
    private char gender;
    private float weight;
    private float height;
    private String medicalConditions;
    private LocalDate registrationDate;
    private boolean active = true;
}