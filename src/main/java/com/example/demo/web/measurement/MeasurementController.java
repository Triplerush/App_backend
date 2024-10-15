package com.example.demo.web.measurement;

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

    @GetMapping("/{patientId}")
    public List<MeasurementDTO> listMeasurements(@PathVariable Long patientId) {
        return measurementService.listByPatientId(patientId);
    }

    @PostMapping
    public MeasurementDTO createMeasurement(@RequestBody MeasurementDTO request) {
        return measurementService.createMeasurement(request);
    }

    @PutMapping("/{id}")
    public MeasurementDTO updateMeasurement(@PathVariable Long id, @RequestBody MeasurementDTO request) {
        return measurementService.updateMeasurement(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMeasurement(@PathVariable Long id) {
        measurementService.deleteMeasurement(id);
    }
}
