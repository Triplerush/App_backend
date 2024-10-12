package com.example.demo.dao.patient.dto;

public record PatientDTO(
        Long id,
        String name,
        String email,
        String doctorName,
        int age,
        char gender,
        float weight,
        float height,
        String medicalConditions,
        boolean active
) {}

