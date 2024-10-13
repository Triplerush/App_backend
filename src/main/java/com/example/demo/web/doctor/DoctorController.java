package com.example.demo.web.doctor;

import com.example.demo.dao.doctor.dto.*;
import com.example.demo.service.doctor.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/doctors")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Page<DoctorDTO>> listDoctors(Pageable pageable) {
        Page<DoctorDTO> doctors = doctorService.listDoctors(pageable);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return doctorService.findDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> registerDoctor(
            @RequestBody @Valid CreateDoctorDTO request,
            UriComponentsBuilder uriComponentsBuilder) {
        DoctorDTO doctor = doctorService.createDoctor(request);
        URI location = uriComponentsBuilder.path("/api/v1/doctors/{id}")
                .buildAndExpand(doctor.idDoctor()).toUri();
        return ResponseEntity.created(location).body(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDoctorDTO request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
