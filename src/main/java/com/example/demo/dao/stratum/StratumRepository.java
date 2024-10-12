package com.example.demo.dao.stratum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StratumRepository extends JpaRepository<Stratum, Long> {
    Page<Stratum> findByActiveTrue(Pageable pageable);
    Optional<Stratum> findByIdStratum(Long id);
}
