// Patient DTOs
package com.example.demo.dao.patient.dto;

public record CreatePatientDTO(
        String name,
        String email,
        String password,
        Long doctorId,
        int age,
        char gender,
        float weight,
        float height,
        String medicalConditions
) {}