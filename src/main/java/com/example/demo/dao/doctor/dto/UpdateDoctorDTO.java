package com.example.demo.dao.doctor.dto;

public record UpdateDoctorDTO(
        String name,
        String specialty,
        String phone,
        String password
) {}
