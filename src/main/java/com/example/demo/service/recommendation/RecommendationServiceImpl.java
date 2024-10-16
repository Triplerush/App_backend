package com.example.demo.service.recommendation;

import com.example.demo.dao.recommendation.*;
import com.example.demo.dao.recommendation.dto.*;
import com.example.demo.dao.stratum.StratumRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final StratumRepository stratumRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<RecommendationDTO> listRecommendations(Pageable pageable) {
        return recommendationRepository.findByActiveTrue(pageable)
                .map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecommendationDTO> findById(Long id) {
        return recommendationRepository.findByIdRecommendation(id)
                .map(this::toDTO);
    }

    @Override
    public RecommendationDTO create(CreateRecommendationDTO request) {
        Recommendation recommendation = new Recommendation();
        recommendation.setContent(request.content());
        recommendation.setActive(true);

        return toDTO(recommendationRepository.save(recommendation));
    }

    @Override
    public RecommendationDTO update(Long id, CreateRecommendationDTO request) {
        return recommendationRepository.findById(id)
                .map(recommendation -> {
                    recommendation.setContent(request.content());
                    return toDTO(recommendationRepository.save(recommendation));
                })
                .orElseThrow(() -> new RuntimeException("Recommendation not found."));
    }

    @Override
    public void delete(Long id) {
        recommendationRepository.findById(id)
                .ifPresentOrElse(recommendation -> {
                    recommendation.setActive(false);
                    recommendationRepository.save(recommendation);
                }, () -> {
                    throw new RuntimeException("Recommendation not found.");
                });
    }

    private RecommendationDTO toDTO(Recommendation recommendation) {
        return new RecommendationDTO(
                recommendation.getIdRecommendation(),
                recommendation.getContent(),
                recommendation.isActive()
        );
    }
}