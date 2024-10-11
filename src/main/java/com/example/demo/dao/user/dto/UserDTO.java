package com.example.demo.dao.user.dto;

import com.example.demo.dao.user.Role;

public record UserDTO(
        Long idUser,
        String name,
        String email,
        Role role,
        Boolean active,
        String token
) {}
