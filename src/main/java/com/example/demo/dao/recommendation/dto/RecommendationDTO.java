package com.example.demo.dao.recommendation.dto;

import java.util.Set;

public record RecommendationDTO(
        Long id,
        String content,
        boolean active
) { }
