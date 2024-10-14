package com.example.demo.web.recommendation;

import com.example.demo.dao.recommendation.dto.*;
import com.example.demo.service.recommendation.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<Page<RecommendationDTO>> listRecommendations(Pageable pageable) {
        return ResponseEntity.ok(recommendationService.listRecommendations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable Long id) {
        return recommendationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> create(
            @RequestBody @Valid CreateRecommendationDTO request,
            UriComponentsBuilder uriBuilder) {
        RecommendationDTO recommendation = recommendationService.create(request);
        URI location = uriBuilder.path("/api/v1/recommendations/{id}")
                .buildAndExpand(recommendation.id()).toUri();
        return ResponseEntity.created(location).body(recommendation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecommendationDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CreateRecommendationDTO request) {
        return ResponseEntity.ok(recommendationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
