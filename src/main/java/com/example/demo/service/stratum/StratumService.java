package com.example.demo.service.stratum;

import com.example.demo.dao.stratum.dto.CreateStratumDTO;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.stratum.dto.UpdateStratumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StratumService {
    Page<StratumDTO> listStrata(Pageable pageable);
    Optional<StratumDTO> findStratumById(Long id);
    StratumDTO createStratum(CreateStratumDTO request);
    StratumDTO updateStratum(Long id, UpdateStratumDTO request);
    void deleteStratum(Long id);
}

