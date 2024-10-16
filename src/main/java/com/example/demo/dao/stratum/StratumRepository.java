package com.example.demo.dao.stratum;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StratumRepository extends JpaRepository<Stratum, Long> {

    @EntityGraph(attributePaths = "recommendations")
    Page<Stratum> findByActiveTrue(Pageable pageable);

    @EntityGraph(attributePaths = "recommendations")
    Optional<Stratum> findByIdStratum(Long id);
}