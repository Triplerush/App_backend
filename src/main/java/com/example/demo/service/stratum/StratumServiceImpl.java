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
                .map(stratum -> new StratumDTO(
                        stratum.getIdStratum(),
                        stratum.getStratumName(),
                        stratum.getDescription(),
                        stratum.isActive()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StratumDTO> findStratumById(Long id) {
        return stratumRepository.findByIdStratum(id)
                .map(stratum -> new StratumDTO(
                        stratum.getIdStratum(),
                        stratum.getStratumName(),
                        stratum.getDescription(),
                        stratum.isActive()
                ));
    }

    @Override
    public StratumDTO createStratum(CreateStratumDTO request) {
        Stratum stratum = new Stratum();
        stratum.setStratumName(request.stratumName());
        stratum.setDescription(request.description());
        stratum.setRecommendations(request.recommendations());
        stratum.setActive(true);

        stratum = stratumRepository.save(stratum);
        return new StratumDTO(
                stratum.getIdStratum(),
                stratum.getStratumName(),
                stratum.getDescription(),
                stratum.isActive()
        );
    }

    @Override
    @Transactional
    public StratumDTO updateStratum(Long id, UpdateStratumDTO request) {
        Stratum stratum = stratumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stratum not found with ID: " + id));

        stratum.setStratumName(request.stratumName());
        stratum.setDescription(request.description());

        Set<Recommendation> recommendations = new HashSet<>();
        for (Long recId : request.recommendationIds()) {
            Recommendation recommendation = recommendationRepository.findById(recId)
                    .orElseThrow(() -> new RuntimeException("Recommendation not found with ID: " + recId));
            recommendations.add(recommendation);
        }
        stratum.setRecommendations(recommendations);

        Stratum updatedStratum = stratumRepository.save(stratum);

        return new StratumDTO(
                updatedStratum.getIdStratum(),
                updatedStratum.getStratumName(),
                updatedStratum.getDescription(),
                updatedStratum.isActive()
        );
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
}

