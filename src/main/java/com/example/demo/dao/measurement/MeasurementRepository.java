package com.example.demo.dao.measurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Page<Measurement> findByActiveTrue(Pageable pagination);
    List<Measurement> findByPatient_idPatientAndActiveTrue(Long patientId);
}
