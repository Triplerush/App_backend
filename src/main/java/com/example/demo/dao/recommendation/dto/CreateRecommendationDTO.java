package com.example.demo.dao.recommendation.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRecommendationDTO(
        @NotBlank String content
) {
}