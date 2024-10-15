package com.example.demo.dao.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Page<Medication> findByActiveTrue(Pageable pagination);
    List<Medication> findByPatient_idPatientAndActiveTrue(Long patientId);

}
