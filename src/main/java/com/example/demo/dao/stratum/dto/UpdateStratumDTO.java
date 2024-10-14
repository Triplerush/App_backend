package com.example.demo.dao.stratum.dto;

import java.util.Set;

public record UpdateStratumDTO(
        String stratumName,
        String description,
        Set<Long> recommendationIds // Cambia a Set<Long> para solo incluir los IDs
) {
}
