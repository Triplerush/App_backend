package com.example.demo.dao.doctor.dto;

public record ListDoctorDTO(
        Long idDoctor,
        String name,
        String email,
        String specialty,
        String phone
) {}