package com.example.demo.service.medication;

import com.example.demo.dao.medication.dto.CreateMedicationDTO;
import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.dao.medication.dto.UpdateMedicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicationService {
    List<MedicationDTO> listByPatientId(Long patientId);
    Page<MedicationDTO> listMedications(Pageable pageable);
    MedicationDTO createMedication(CreateMedicationDTO request);
    Optional<MedicationDTO> findMedicationById(Long id);
    MedicationDTO updateMedication(Long id, UpdateMedicationDTO request);
    void deleteMedication(Long id);
}
