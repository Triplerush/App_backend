package com.example.demo.service.medication;

import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.dao.patient.dto.CreatePatientDTO;
import com.example.demo.dao.patient.dto.PatientDTO;
import com.example.demo.dao.patient.dto.UpdatePatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicationService {
    List<MedicationDTO> listByPatientId(Long patientId);
    MedicationDTO createMedication(MedicationDTO request);
    MedicationDTO updateMedication(Long id, MedicationDTO request);
    void deleteMedication(Long id);
}
