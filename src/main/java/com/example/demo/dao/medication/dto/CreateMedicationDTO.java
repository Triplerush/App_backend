package com.example.demo.dao.medication.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record CreateMedicationDTO(
        Long idMedication,
        @NotNull Long patientId,
        @NotNull String medicationName,
        @NotNull String dosage,
        @NotNull String frequency,
        @NotNull Date treatmentStart,
        Date treatmentEnd,
        boolean active
) {}
