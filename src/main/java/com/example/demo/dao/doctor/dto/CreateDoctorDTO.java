// Doctor DTOs
package com.example.demo.dao.doctor.dto;

public record CreateDoctorDTO(
        String username,
        String email,
        String password,
        String specialty,
        String phone
) {}
