package com.example.demo.dao.medication.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record UpdateMedicationDTO(
        Long patientId,
        String medicationName,
        String dosage,
        String frequency,
        Date treatmentStart,
        Date treatmentEnd
) {}

