package com.example.demo.service.doctor;

import com.example.demo.dao.doctor.Doctor;
import com.example.demo.dao.doctor.DoctorRepository;
import com.example.demo.dao.doctor.dto.CreateDoctorDTO;
import com.example.demo.dao.doctor.dto.DoctorDTO;
import com.example.demo.dao.doctor.dto.UpdateDoctorDTO;
import com.example.demo.dao.user.Role;
import com.example.demo.dao.user.User;
import com.example.demo.dao.user.dto.CreateUserDTO;
import com.example.demo.dao.user.dto.UpdateUserDTO;
import com.example.demo.dao.user.dto.UserDTO;
import com.example.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorDTO> listDoctors(Pageable pageable) {
        return doctorRepository.findByActiveTrue(pageable)
                .map(this::mapToDoctorDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(this::mapToDoctorDTO);
    }

    @Override
    public DoctorDTO createDoctor(CreateDoctorDTO request) {
        User user = userService.createUser(new CreateUserDTO(
                request.username(),
                request.email(),
                request.password(),
                Role.DOCTOR,
                null
        ));

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialty(request.specialty());
        doctor.setPhone(request.phone());

        return mapToDoctorDTO(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDTO updateDoctor(Long id, UpdateDoctorDTO request) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    Optional.ofNullable(request.specialty()).ifPresent(doctor::setSpecialty);
                    Optional.ofNullable(request.phone()).ifPresent(doctor::setPhone);

                    User user = doctor.getUser();
                    if (user != null) {
                        Optional.ofNullable(request.username()).ifPresent(user::setName);
                        Optional.ofNullable(request.password()).ifPresent(user::setPassword);
                    }

                    return mapToDoctorDTO(doctorRepository.save(doctor));
                })
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.findById(id)
                .ifPresentOrElse(this::deactivateDoctor,
                        () -> { throw new RuntimeException("Doctor not found with ID: " + id); });
    }

    private void deactivateDoctor(Doctor doctor) {
        doctor.setActive(false);
        User user = doctor.getUser();
        user.setActive(false);
        userService.updateUser(user.getIdUser(), new UpdateUserDTO(user.getName(), user.getPassword()));
        doctorRepository.save(doctor);
    }

    private DoctorDTO mapToDoctorDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getIdDoctor(),
                mapToUserDTO(doctor.getUser()),
                doctor.getSpecialty(),
                doctor.getPhone(),
                doctor.isActive()
        );
    }

    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getToken()
        );
    }
}