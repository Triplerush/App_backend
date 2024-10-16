package com.example.demo.web.medication;

import com.example.demo.dao.doctor.dto.DoctorDTO;
import com.example.demo.dao.medication.dto.CreateMedicationDTO;
import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.dao.medication.dto.UpdateMedicationDTO;
import com.example.demo.service.medication.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medications")
@AllArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/patient/{patientId}")
    public List<MedicationDTO> listMedicationsByPatient(@PathVariable Long patientId) {
        return medicationService.listByPatientId(patientId);
    }

    @GetMapping("/{medicationId}")
    public Optional<MedicationDTO> listMedicationsById(@PathVariable Long medicationId) {
        return medicationService.findMedicationById(medicationId);
    }
    
    @GetMapping
    public ResponseEntity<Page<MedicationDTO>> listMedications(Pageable pageable) {
        Page<MedicationDTO> medications = medicationService.listMedications(pageable);
        return ResponseEntity.ok(medications);
    }

    @PostMapping
    public MedicationDTO createMedication(@RequestBody CreateMedicationDTO request) {
        return medicationService.createMedication(request);
    }

    @PutMapping("/{id}")
    public MedicationDTO updateMedication(@PathVariable Long id, @RequestBody UpdateMedicationDTO request) {
        return medicationService.updateMedication(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}
