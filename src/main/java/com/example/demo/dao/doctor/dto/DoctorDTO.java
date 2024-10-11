// DoctorDTO.java
package com.example.demo.dao.doctor.dto;

public record DoctorDTO(
        Long idDoctor,
        String name,
        String email,
        String specialty,
        String phone,
        boolean active,
        boolean userActive
) {}
