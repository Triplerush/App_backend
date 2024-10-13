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
                .map(doctor -> new DoctorDTO(
                        doctor.getIdDoctor(),
                        new UserDTO(
                                doctor.getUser().getIdUser(),
                                doctor.getUser().getName(),
                                doctor.getUser().getEmail(),
                                doctor.getUser().getRole(),
                                doctor.getUser().isActive(),
                                doctor.getUser().getToken()
                        ),
                        doctor.getSpecialty(),
                        doctor.getPhone(),
                        doctor.isActive()
                ));
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorDTO> findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> new DoctorDTO(
                        doctor.getIdDoctor(),
                        new UserDTO( // Creación del UserDTO directamente aquí
                                doctor.getUser().getIdUser(),
                                doctor.getUser().getName(),
                                doctor.getUser().getEmail(),
                                doctor.getUser().getRole(),
                                doctor.getUser().isActive(),
                                doctor.getUser().getToken()
                        ),
                        doctor.getSpecialty(),
                        doctor.getPhone(),
                        doctor.isActive()
                ));
    }

    @Override
    public DoctorDTO createDoctor(CreateDoctorDTO request) {
        // Crear usuario
        CreateUserDTO user = new CreateUserDTO(
                request.username(),
                request.email(),
                request.password(),
                Role.DOCTOR,
                null
        );

        // Crear doctor
        Doctor doctor = new Doctor();
        doctor.setUser(userService.createUser(user));
        doctor.setSpecialty(request.specialty());
        doctor.setPhone(request.phone());
        doctor = doctorRepository.save(doctor);

        return mapToDoctorDTO(doctor);
    }

    @Override
    public DoctorDTO updateDoctor(Long id, UpdateDoctorDTO request) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setSpecialty(request.specialty());
                    doctor.setPhone(request.phone());

                    User user = doctor.getUser();
                    if (request.username() != null) {
                        user.setName(request.username());
                    }

                    if (request.password() != null && !request.password().isEmpty()) {
                        user.setPassword(request.password());
                    }

                    userService.updateUser(user.getIdUser(), new UpdateUserDTO(
                            user.getName(),
                            user.getPassword()
                    ));

                    Doctor updatedDoctor = doctorRepository.save(doctor);

                    return new DoctorDTO(
                            updatedDoctor.getIdDoctor(),
                            new UserDTO( // Creación del UserDTO directamente aquí
                                    updatedDoctor.getUser().getIdUser(),
                                    updatedDoctor.getUser().getName(),
                                    updatedDoctor.getUser().getEmail(),
                                    updatedDoctor.getUser().getRole(),
                                    updatedDoctor.getUser().isActive(),
                                    updatedDoctor.getUser().getToken()
                            ),
                            updatedDoctor.getSpecialty(),
                            updatedDoctor.getPhone(),
                            updatedDoctor.isActive() // Estado activo del doctor
                    );
                }).orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.findById(id)
                .ifPresentOrElse(
                        doctor -> {
                            // Desactivar al doctor
                            doctor.setActive(false);

                            // Desactivar al usuario asociado
                            User user = doctor.getUser();
                            user.setActive(false);

                            // Guardamos ambas entidades
                            userService.updateUser(user.getIdUser(), new UpdateUserDTO(
                                    user.getName(),
                                    user.getPassword()
                            ));

                            doctorRepository.save(doctor);
                        },
                        () -> {
                            throw new RuntimeException("Doctor not found with ID: " + id);
                        }
                );
    }


    private DoctorDTO mapToDoctorDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getIdDoctor(),
                new UserDTO(
                        doctor.getUser().getIdUser(),
                        doctor.getUser().getName(),
                        doctor.getUser().getEmail(),
                        doctor.getUser().getRole(),
                        doctor.getUser().isActive(),
                        doctor.getUser().getToken()
                ),
                doctor.getSpecialty(),
                doctor.getPhone(),
                doctor.isActive()
        );
    }
}

