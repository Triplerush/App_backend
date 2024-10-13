package com.example.demo.service.patient;

import com.example.demo.dao.patient.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {
    Page<PatientDTO> listPatients(Pageable pageable);
    Optional<PatientDTO> findPatientById(Long id);
    PatientDTO createPatient(CreatePatientDTO request);
    PatientDTO updatePatient(Long id, UpdatePatientDTO request);
    void deletePatient(Long id);
}
