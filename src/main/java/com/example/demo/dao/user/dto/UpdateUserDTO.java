package com.example.demo.dao.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
        @Size(max = 255) String username,
        @Size(min = 8, max = 255) String password
) {}


