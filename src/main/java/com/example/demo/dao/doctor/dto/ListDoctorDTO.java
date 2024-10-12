package com.example.demo.dao.doctor.dto;

public record ListDoctorDTO(
        Long idDoctor,
        String username,
        String email,
        String specialty,
        String phone
) {}