package com.example.demo.dao.stratum.dto;

import java.util.List;

public record StratumDTO(
        Long idStratum,
        String stratumName,
        String description,
        boolean active,
        List<String> recommendationDescriptions
) {}
