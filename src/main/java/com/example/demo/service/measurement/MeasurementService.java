package com.example.demo.service.measurement;

import com.example.demo.dao.measurement.dto.CreateMeasurementDTO;
import com.example.demo.dao.measurement.dto.MeasurementDTO;
import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    List<MeasurementDTO> listByPatientId(Long patientId);
    MeasurementDTO createMeasurement(CreateMeasurementDTO request);
    Optional<MeasurementDTO> getMeasurementById(Long id);
    List<MeasurementDTO> listMeasurements();
    MeasurementDTO updateMeasurement(Long id, CreateMeasurementDTO request);
    void deleteMeasurement(Long id);
}
