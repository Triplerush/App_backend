// Doctor DTOs
package com.example.demo.dao.doctor.dto;

public record CreateDoctorDTO(
        String name,
        String email,
        String password,
        String specialty,
        String phone
) {}
