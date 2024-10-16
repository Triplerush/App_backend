package com.example.demo.dao.measurement.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record CreateMeasurementDTO(
        @NotNull Long patientId,
        @NotNull Date measurementDate,
        int systolicPressure,
        int diastolicPressure,
        int heartRate,
        String notes
) {}
