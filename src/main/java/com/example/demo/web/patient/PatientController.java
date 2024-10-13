package com.example.demo.web.patient;

import com.example.demo.dao.patient.dto.CreatePatientDTO;
import com.example.demo.dao.patient.dto.PatientDTO;
import com.example.demo.dao.patient.dto.UpdatePatientDTO;
import com.example.demo.service.patient.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<Page<PatientDTO>> listPatients(Pageable pageable) {
        Page<PatientDTO> patients = patientService.listPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> getPatientById(@PathVariable Long id) {
        Optional<PatientDTO> patient = patientService.findPatientById(id);
        return patient.isPresent() ? ResponseEntity.ok(patient) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody @Valid CreatePatientDTO request, UriComponentsBuilder uriComponentsBuilder) {
        PatientDTO patient = patientService.createPatient(request);
        URI location = uriComponentsBuilder.path("/api/v1/patients/{id}").buildAndExpand(patient.idPatient()).toUri();
        return ResponseEntity.created(location).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody @Valid UpdatePatientDTO request) {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
