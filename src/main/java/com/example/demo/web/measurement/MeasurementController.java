package com.example.demo.web.measurement;

import com.example.demo.dao.measurement.dto.CreateMeasurementDTO;
import com.example.demo.dao.measurement.dto.MeasurementDTO;
import com.example.demo.service.measurement.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurements")
@AllArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping("/patient/{patientId}")
    public List<MeasurementDTO> listMeasurements(@PathVariable Long patientId) {
        return measurementService.listByPatientId(patientId);
    }

    @PostMapping
    public MeasurementDTO createMeasurement(@RequestBody CreateMeasurementDTO request) {
        return measurementService.createMeasurement(request);
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurementById(@PathVariable Long id) {
        return measurementService.getMeasurementById(id)
                .orElseThrow(() -> new RuntimeException("Measurement not found or not active"));
    }

    @GetMapping
    public List<MeasurementDTO> listMeasurements() {
        return measurementService.listMeasurements();
    }

    @PutMapping("/{id}")
    public MeasurementDTO updateMeasurement(@PathVariable Long id, @RequestBody CreateMeasurementDTO request) {
        return measurementService.updateMeasurement(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMeasurement(@PathVariable Long id) {
        measurementService.deleteMeasurement(id);
    }
}
