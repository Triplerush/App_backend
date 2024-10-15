package com.example.demo.service.measurement;

import com.example.demo.dao.measurement.dto.MeasurementDTO;
import java.util.List;

public interface MeasurementService {
    List<MeasurementDTO> listByPatientId(Long patientId);
    MeasurementDTO createMeasurement(MeasurementDTO request);
    MeasurementDTO updateMeasurement(Long id, MeasurementDTO request);
    void deleteMeasurement(Long id);
}
