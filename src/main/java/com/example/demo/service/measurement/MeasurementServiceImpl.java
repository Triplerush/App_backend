package com.example.demo.service.measurement;

import com.example.demo.dao.measurement.Measurement;
import com.example.demo.dao.measurement.MeasurementRepository;
import com.example.demo.dao.measurement.dto.CreateMeasurementDTO;
import com.example.demo.dao.measurement.dto.MeasurementDTO;
import com.example.demo.dao.patient.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementDTO> listByPatientId(Long patientId) {
        return measurementRepository.findByPatient_idPatientAndActiveTrue(patientId)
                .stream()
                .map(measurement -> new MeasurementDTO(
                        measurement.getIdMeasurement(),
                        measurement.getPatient().getIdPatient(),
                        measurement.getMeasurementDate(),
                        measurement.getSystolicPressure(),
                        measurement.getDiastolicPressure(),
                        measurement.getHeartRate(),
                        measurement.getNotes(),
                        measurement.isActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MeasurementDTO> getMeasurementById(Long id) {
        return measurementRepository.findByIdMeasurement(id)
                .map(this::mapToMeasurementDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasurementDTO> listMeasurements() {
        return measurementRepository.findByActiveTrue()
                .stream()
                .map(this::mapToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeasurementDTO createMeasurement(CreateMeasurementDTO request) {
        Measurement measurement = new Measurement();
        measurement.setPatient(patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));
        measurement.setMeasurementDate(request.measurementDate());
        measurement.setSystolicPressure(request.systolicPressure());
        measurement.setDiastolicPressure(request.diastolicPressure());
        measurement.setHeartRate(request.heartRate());
        measurement.setNotes(request.notes());
        measurement.setActive(true);

        measurement = measurementRepository.save(measurement);
        return mapToMeasurementDTO(measurement);
    }

    @Override
    public MeasurementDTO updateMeasurement(Long id, CreateMeasurementDTO request) {
        return measurementRepository.findById(id)
                .map(measurement -> {
                    measurement.setMeasurementDate(request.measurementDate());
                    measurement.setSystolicPressure(request.systolicPressure());
                    measurement.setDiastolicPressure(request.diastolicPressure());
                    measurement.setHeartRate(request.heartRate());
                    measurement.setNotes(request.notes());
                    Measurement updatedMeasurement = measurementRepository.save(measurement);
                    return mapToMeasurementDTO(updatedMeasurement);
                }).orElseThrow(() -> new RuntimeException("Measurement not found"));
    }

    @Override
    public void deleteMeasurement(Long id) {
        measurementRepository.findById(id).ifPresent(measurement -> {
            measurement.setActive(false);
            measurementRepository.save(measurement);
        });
    }

    private MeasurementDTO mapToMeasurementDTO(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getIdMeasurement(),
                measurement.getPatient().getIdPatient(),
                measurement.getMeasurementDate(),
                measurement.getSystolicPressure(),
                measurement.getDiastolicPressure(),
                measurement.getHeartRate(),
                measurement.getNotes(),
                measurement.isActive()
        );
    }
}
