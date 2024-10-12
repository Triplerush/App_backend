package com.example.demo.dao.doctor.dto;

public record UpdateDoctorDTO(
        String username,
        String specialty,
        String phone,
        String password
) {}
