package com.example.demo.dao.patient.dto;

import com.example.demo.dao.doctor.dto.DoctorDTO;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.user.dto.UserDTO;

import java.util.Date;

public record PatientDTO(
        Long idPatient,
        UserDTO user,
        DoctorDTO doctor,
        StratumDTO stratum,
        int age,
        char gender,
        float weight,
        float height,
        String medicalConditions,
        boolean active,
        Date registrationDate
) {}
