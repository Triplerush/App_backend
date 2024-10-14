package com.example.demo.dao.recommendation.dto;

import java.util.Set;

public record CreateRecommendationDTO(String content, Set<Long> stratumIds
                                      ) {}