// Doctor DTOs
package com.example.demo.dao.doctor.dto;

import jakarta.validation.constraints.NotNull;

public record CreateDoctorDTO(
        @NotNull String username,
        @NotNull String email,
        @NotNull String password,
        String specialty,
        String phone
) {}
