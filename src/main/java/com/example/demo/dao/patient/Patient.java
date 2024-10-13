package com.example.demo.dao.patient;

import com.example.demo.dao.user.User;
import com.example.demo.dao.doctor.Doctor;
import com.example.demo.dao.stratum.Stratum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "id_stratum")
    private Stratum stratum;

    private int age;
    private char gender;
    private float weight;
    private float height;
    @Column(name = "medical_conditions")
    private String medicalConditions;
    private boolean active = true;
    @Column(name = "registration_date")
    private Date registrationDate;
}
