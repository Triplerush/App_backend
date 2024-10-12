package com.example.demo.dao.user.dto;

import com.example.demo.dao.user.Role;

public record UserDTO(
        Long idUser,
        String username,
        String email,
        Role role,
        Boolean active,
        String token
) {}
