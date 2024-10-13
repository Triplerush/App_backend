package com.example.demo.dao.patient.dto;

public record UpdatePatientDTO(
        Long idDoctor,
        Long idStratum,
        int age,
        char gender,
        float weight,
        float height,
        String medicalConditions
) {}
