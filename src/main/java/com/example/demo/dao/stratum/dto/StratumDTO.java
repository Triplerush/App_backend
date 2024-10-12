package com.example.demo.dao.stratum.dto;

public record StratumDTO(
        Long idStratum,
        String stratumName,
        String description,
        boolean active
) {}
