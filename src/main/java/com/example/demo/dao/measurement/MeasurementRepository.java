package com.example.demo.dao.measurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByActiveTrue();
    List<Measurement> findByPatient_idPatientAndActiveTrue(Long patientId);
    Optional<Measurement> findByIdMeasurement(Long id);
}
