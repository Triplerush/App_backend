package com.example.demo.dao.patient.dto;

public record ListPatientDTO(
        Long id,
        String name,
        String email,
        String doctorName,
        String patientCode
) {}
