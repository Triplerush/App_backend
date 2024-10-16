package com.example.demo.service.medication;

import com.example.demo.dao.medication.Medication;
import com.example.demo.dao.medication.MedicationRepository;
import com.example.demo.dao.medication.dto.CreateMedicationDTO;
import com.example.demo.dao.medication.dto.MedicationDTO;
import com.example.demo.dao.medication.dto.UpdateMedicationDTO;
import com.example.demo.dao.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicationDTO> listByPatientId(Long patientId) {
        return medicationRepository.findByPatient_idPatientAndActiveTrue(patientId)
                .stream()
                .map(this::mapToMedicationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationDTO> listMedications(Pageable pageable) {
        return medicationRepository.findByActiveTrue(pageable)
                .map(this::mapToMedicationDTO);
    }

    @Override
    public MedicationDTO createMedication(CreateMedicationDTO request) {
        Medication medication = new Medication();
        medication.setPatient(patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));
        medication.setMedicationName(request.medicationName());
        medication.setDosage(request.dosage());
        medication.setFrequency(request.frequency());
        medication.setTreatmentStart(request.treatmentStart());
        medication.setTreatmentEnd(request.treatmentEnd());
        medication.setActive(request.active());

        Medication savedMedication = medicationRepository.save(medication);
        return mapToMedicationDTO(savedMedication);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationDTO> findMedicationById(Long id) {
        return medicationRepository.findById(id)
                .map(this::mapToMedicationDTO);
    }

    @Override
    public MedicationDTO updateMedication(Long id, UpdateMedicationDTO request) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        Optional.ofNullable(request.medicationName()).ifPresent(medication::setMedicationName);
        Optional.ofNullable(request.dosage()).ifPresent(medication::setDosage);
        Optional.ofNullable(request.frequency()).ifPresent(medication::setFrequency);
        Optional.ofNullable(request.treatmentStart()).ifPresent(medication::setTreatmentStart);
        Optional.ofNullable(request.treatmentEnd()).ifPresent(medication::setTreatmentEnd);

        Medication updatedMedication = medicationRepository.save(medication);
        return mapToMedicationDTO(updatedMedication);
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