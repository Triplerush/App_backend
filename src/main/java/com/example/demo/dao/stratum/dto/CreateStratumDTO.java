package com.example.demo.dao.stratum.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStratumDTO(
        @NotBlank String stratumName,
        String description
) {}

