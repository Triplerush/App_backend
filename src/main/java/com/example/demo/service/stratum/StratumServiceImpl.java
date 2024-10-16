package com.example.demo.service.stratum;

import com.example.demo.dao.recommendation.Recommendation;
import com.example.demo.dao.recommendation.RecommendationRepository;
import com.example.demo.dao.stratum.Stratum;
import com.example.demo.dao.stratum.StratumRepository;
import com.example.demo.dao.stratum.dto.CreateStratumDTO;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.stratum.dto.UpdateStratumDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StratumServiceImpl implements StratumService {

    private final StratumRepository stratumRepository;
    private final RecommendationRepository recommendationRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<StratumDTO> listStrata(Pageable pageable) {
        return stratumRepository.findByActiveTrue(pageable)
                .map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StratumDTO> findStratumById(Long id) {
        return stratumRepository.findByIdStratum(id)
                .map(this::toDTO);
    }

    @Override
    public StratumDTO createStratum(CreateStratumDTO request) {
        Stratum stratum = new Stratum();
        stratum.setStratumName(request.stratumName());
        stratum.setDescription(request.description());
        stratum.setRecommendations(request.recommendations());
        stratum.setActive(true);

        return toDTO(stratumRepository.save(stratum));
    }

    @Override
    @Transactional
    public StratumDTO updateStratum(Long id, UpdateStratumDTO request) {
        Stratum stratum = stratumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stratum not found with ID: " + id));

        Optional.ofNullable(request.stratumName()).ifPresent(stratum::setStratumName);
        Optional.ofNullable(request.description()).ifPresent(stratum::setDescription);

        if (request.recommendationIds() != null) {
            Set<Recommendation> recommendations = request.recommendationIds().stream()
                    .map(recId -> recommendationRepository.findById(recId)
                            .orElseThrow(() -> new RuntimeException("Recommendation not found with ID: " + recId)))
                    .collect(Collectors.toSet());
            stratum.setRecommendations(recommendations);
        }

        return toDTO(stratumRepository.save(stratum));
    }

    @Override
    public void deleteStratum(Long id) {
        stratumRepository.findById(id)
                .ifPresentOrElse(stratum -> {
                    stratum.setActive(false);
                    stratumRepository.save(stratum);
                }, () -> {
                    throw new RuntimeException("Stratum not found with ID: " + id);
                });
    }

    private StratumDTO toDTO(Stratum stratum) {
        List<String> recommendationDescriptions = Optional.ofNullable(stratum.getRecommendations())
                .orElseGet(HashSet::new)
                .stream()
                .map(Recommendation::getContent)
                .collect(Collectors.toList());

        return new StratumDTO(
                stratum.getIdStratum(),
                stratum.getStratumName(),
                stratum.getDescription(),
                stratum.isActive(),
                recommendationDescriptions
        );
    }
}
