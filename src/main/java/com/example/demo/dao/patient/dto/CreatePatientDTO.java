package com.example.demo.dao.patient.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePatientDTO(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password,
        @NotNull int age,
        char gender,
        float weight,
        float height
) {}
