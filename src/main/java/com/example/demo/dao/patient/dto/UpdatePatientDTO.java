package com.example.demo.dao.patient.dto;

public record UpdatePatientDTO(
        String name,
        String password,
        Long doctorId,
        float weight,
        float height,
        String medicalConditions
) {}
