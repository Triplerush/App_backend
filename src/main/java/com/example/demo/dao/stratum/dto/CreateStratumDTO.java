package com.example.demo.dao.stratum.dto;

import com.example.demo.dao.recommendation.Recommendation;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateStratumDTO(
        @NotBlank String stratumName,
        Set<Recommendation> recommendations,
        String description
) {}

