package com.example.demo.web.medication;

import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.service.medication.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@AllArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/{patientId}")
    public List<MedicationDTO> listMedications(@PathVariable Long patientId) {
        return medicationService.listByPatientId(patientId);
    }

    @PostMapping
    public MedicationDTO createMedication(@RequestBody MedicationDTO request) {
        return medicationService.createMedication(request);
    }

    @PutMapping("/{id}")
    public MedicationDTO updateMedication(@PathVariable Long id, @RequestBody MedicationDTO request) {
        return medicationService.updateMedication(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}
