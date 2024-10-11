package com.example.demo.dao.user.dto;

import com.example.demo.dao.user.Role;

public record ListUserDTO(
        Long idUser,
        String name,
        String email,
        Role role,
        String token
) {}

