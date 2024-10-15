package com.example.demo.service.medication;

import com.example.demo.dao.medication.Medication;
import com.example.demo.dao.medication.MedicationRepository;
import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.dao.patient.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicationDTO> listByPatientId(Long patientId) {
        return medicationRepository.findByPatient_idPatientAndActiveTrue(patientId)
                .stream()
                .map(med -> new MedicationDTO(
                        med.getIdMedication(),
                        med.getPatient().getIdPatient(),
                        med.getMedicationName(),
                        med.getDosage(),
                        med.getFrequency(),
                        med.getTreatmentStart(),
                        med.getTreatmentEnd(),
                        med.isActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public MedicationDTO createMedication(MedicationDTO request) {
        Medication medication = new Medication();
        medication.setPatient(patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));
        medication.setMedicationName(request.medicationName());
        medication.setDosage(request.dosage());
        medication.setFrequency(request.frequency());
        medication.setTreatmentStart(request.treatmentStart());
        medication.setTreatmentEnd(request.treatmentEnd());
        medication.setActive(request.active());

        medication = medicationRepository.save(medication);
        return mapToMedicationDTO(medication);
    }

    @Override
    public MedicationDTO updateMedication(Long id, MedicationDTO request) {
        return medicationRepository.findById(id)
                .map(medication -> {
                    medication.setMedicationName(request.medicationName());
                    medication.setDosage(request.dosage());
                    medication.setFrequency(request.frequency());
                    medication.setTreatmentStart(request.treatmentStart());
                    medication.setTreatmentEnd(request.treatmentEnd());
                    Medication updatedMedication = medicationRepository.save(medication);
                    return mapToMedicationDTO(updatedMedication);
                }).orElseThrow(() -> new RuntimeException("Medication not found"));
    }

    @Override
    public void deleteMedication(Long id) {
        medicationRepository.findById(id).ifPresent(medication -> {
            medication.setActive(false);
            medicationRepository.save(medication);
        });
    }

    private MedicationDTO mapToMedicationDTO(Medication medication) {
        return new MedicationDTO(
                medication.getIdMedication(),
                medication.getPatient().getIdPatient(),
                medication.getMedicationName(),
                medication.getDosage(),
                medication.getFrequency(),
                medication.getTreatmentStart(),
                medication.getTreatmentEnd(),
                medication.isActive()
        );
    }
}
