package com.example.demo.service.recommendation;

import com.example.demo.dao.recommendation.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface RecommendationService {
    Page<RecommendationDTO> listRecommendations(Pageable pageable);
    Optional<RecommendationDTO> findById(Long id);
    RecommendationDTO create(CreateRecommendationDTO request);
    RecommendationDTO update(Long id, CreateRecommendationDTO request);
    void delete(Long id);
}
