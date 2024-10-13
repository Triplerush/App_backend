// DoctorService.java
package com.example.demo.service.doctor;

import com.example.demo.dao.doctor.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DoctorService {
    Page<DoctorDTO> listDoctors(Pageable pageable);
    Optional<DoctorDTO> findDoctorById(Long id);
    DoctorDTO createDoctor(CreateDoctorDTO request);
    DoctorDTO updateDoctor(Long id, UpdateDoctorDTO request);
    void deleteDoctor(Long id);
}