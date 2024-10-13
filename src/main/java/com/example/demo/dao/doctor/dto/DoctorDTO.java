package com.example.demo.dao.doctor.dto;

import com.example.demo.dao.user.dto.UserDTO;

public record DoctorDTO(
        Long idDoctor,
        UserDTO user,
        String specialty,
        String phone,
        boolean active
) {}
