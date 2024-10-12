package com.example.demo.web.stratum;

import com.example.demo.dao.stratum.dto.CreateStratumDTO;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.stratum.dto.UpdateStratumDTO;
import com.example.demo.service.stratum.StratumService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stratum")
@AllArgsConstructor
public class StratumController {

    private final StratumService stratumService;

    @GetMapping
    public ResponseEntity<Page<StratumDTO>> listStrata(Pageable pageable) {
        Page<StratumDTO> strata = stratumService.listStrata(pageable);
        return ResponseEntity.ok(strata);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StratumDTO>> getStratumById(@PathVariable Long id) {
        Optional<StratumDTO> stratum = stratumService.findStratumById(id);
        return stratum.isPresent() ? ResponseEntity.ok(stratum) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StratumDTO> registerStratum(@RequestBody @Valid CreateStratumDTO request, UriComponentsBuilder uriComponentsBuilder) {
        StratumDTO stratum = stratumService.createStratum(request);
        URI location = uriComponentsBuilder.path("/api/v1/strata/{id}").buildAndExpand(stratum.idStratum()).toUri();
        return ResponseEntity.created(location).body(stratum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StratumDTO> updateStratum(@PathVariable Long id, @RequestBody @Valid UpdateStratumDTO request) {
        return ResponseEntity.ok(stratumService.updateStratum(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStratum(@PathVariable Long id) {
        stratumService.deleteStratum(id);
        return ResponseEntity.noContent().build();
    }
}

