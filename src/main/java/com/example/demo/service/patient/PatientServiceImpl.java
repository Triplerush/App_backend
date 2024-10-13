package com.example.demo.service.patient;

import com.example.demo.dao.doctor.DoctorRepository;
import com.example.demo.dao.doctor.dto.DoctorDTO;
import com.example.demo.dao.patient.Patient;
import com.example.demo.dao.patient.PatientRepository;
import com.example.demo.dao.patient.dto.*;
import com.example.demo.dao.stratum.StratumRepository;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.user.Role;
import com.example.demo.dao.user.User;
import com.example.demo.dao.user.UserRepository;
import com.example.demo.dao.user.dto.CreateUserDTO;
import com.example.demo.dao.user.dto.UserDTO;
import com.example.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final StratumRepository stratumRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> listPatients(Pageable pageable) {
        return patientRepository.findByActiveTrue(pageable)
                .map(patient -> new PatientDTO(
                        patient.getIdPatient(),
                        new UserDTO(
                                patient.getUser().getIdUser(),
                                patient.getUser().getName(),
                                patient.getUser().getEmail(),
                                patient.getUser().getRole(),
                                patient.getUser().isActive(),
                                patient.getUser().getToken()
                        ),
                        patient.getDoctor() != null ? new DoctorDTO(
                                patient.getDoctor().getIdDoctor(),
                                new UserDTO(
                                        patient.getDoctor().getUser().getIdUser(),
                                        patient.getDoctor().getUser().getName(),
                                        patient.getDoctor().getUser().getEmail(),
                                        patient.getDoctor().getUser().getRole(),
                                        patient.getDoctor().getUser().isActive(),
                                        patient.getDoctor().getUser().getToken()
                                ),
                                patient.getDoctor().getSpecialty(),
                                patient.getDoctor().getPhone(),
                                patient.getDoctor().isActive()
                        ) : null,
                        patient.getStratum() != null ? new StratumDTO(
                                patient.getStratum().getIdStratum(),
                                patient.getStratum().getStratumName(),
                                patient.getStratum().getDescription(),
                                patient.getStratum().isActive()
                        ) : null,
                        patient.getAge(),
                        patient.getGender(),
                        patient.getWeight(),
                        patient.getHeight(),
                        patient.getMedicalConditions(),
                        patient.isActive(),
                        patient.getRegistrationDate()
                ));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findPatientById(Long id) {
        return patientRepository.findByIdPatient(id)
                .map(patient -> new PatientDTO(
                        patient.getIdPatient(),
                        new UserDTO(
                                patient.getUser().getIdUser(),
                                patient.getUser().getName(),
                                patient.getUser().getEmail(),
                                patient.getUser().getRole(),
                                patient.getUser().isActive(),
                                patient.getUser().getToken()
                        ),
                        patient.getDoctor() != null ? new DoctorDTO(
                                patient.getDoctor().getIdDoctor(),
                                new UserDTO( // Crear el UserDTO directamente aquí
                                        patient.getDoctor().getUser().getIdUser(),
                                        patient.getDoctor().getUser().getName(),
                                        patient.getDoctor().getUser().getEmail(),
                                        patient.getDoctor().getUser().getRole(),
                                        patient.getDoctor().getUser().isActive(),
                                        patient.getDoctor().getUser().getToken()
                                ),
                                patient.getDoctor().getSpecialty(),
                                patient.getDoctor().getPhone(),
                                patient.getDoctor().isActive()
                        ) : null,
                        patient.getStratum() != null ? new StratumDTO(
                                patient.getStratum().getIdStratum(),
                                patient.getStratum().getStratumName(),
                                patient.getStratum().getDescription(),
                                patient.getStratum().isActive()
                        ) : null,
                        patient.getAge(),
                        patient.getGender(),
                        patient.getWeight(),
                        patient.getHeight(),
                        patient.getMedicalConditions(),
                        patient.isActive(),
                        patient.getRegistrationDate()
                ));
    }

    @Override
    public PatientDTO createPatient(CreatePatientDTO request) {
        // Crear usuario
        CreateUserDTO userRequest = new CreateUserDTO(
                request.username(),
                request.email(),
                request.password(),
                Role.PATIENT,
                null
        );

        // Crear usuario
        User user = userService.createUser(userRequest);

        // Crear paciente
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAge(request.age());
        patient.setGender(request.gender());
        patient.setWeight(request.weight());
        patient.setHeight(request.height());
        patient.setRegistrationDate(new java.util.Date());
        patient.setActive(true);

        patient = patientRepository.save(patient);
        return new PatientDTO(
                patient.getIdPatient(),
                new UserDTO(patient.getUser().getIdUser(), patient.getUser().getName(), patient.getUser().getEmail(), patient.getUser().getRole(), patient.getUser().isActive(), patient.getUser().getToken()),
                null,
                null,
                patient.getAge(),
                patient.getGender(),
                patient.getWeight(),
                patient.getHeight(),
                null,
                patient.isActive(),
                patient.getRegistrationDate()
        );
    }

    @Override
    public PatientDTO updatePatient(Long id, UpdatePatientDTO request) {
        return patientRepository.findById(id)
                .map(patient -> {
                    // Actualizar el doctor solo si se proporciona un ID
                    if (request.idDoctor() != null) {
                        patient.setDoctor(doctorRepository.findById(request.idDoctor()).orElse(null));
                    }

                    // Actualizar el estrato solo si se proporciona un ID
                    if (request.idStratum() != null) {
                        patient.setStratum(stratumRepository.findById(request.idStratum()).orElse(null));
                    }

                    // Actualizar otros campos solo si se proporcionan
                    if (request.age() >= 0) { // Asegúrate de que sea un valor válido
                        patient.setAge(request.age());
                    }
                    if (ObjectUtils.isEmpty(request.gender())) {
                        patient.setGender(request.gender());
                    }
                    if (request.weight() >= 0) { // Asegúrate de que sea un valor válido
                        patient.setWeight(request.weight());
                    }
                    if (request.height() >= 0) { // Asegúrate de que sea un valor válido
                        patient.setHeight(request.height());
                    }
                    if (request.medicalConditions() != null) {
                        patient.setMedicalConditions(request.medicalConditions());
                    }

                    // Guardar el paciente actualizado
                    Patient updatedPatient = patientRepository.save(patient);
                    return new PatientDTO(
                            updatedPatient.getIdPatient(),
                            new UserDTO(
                                    updatedPatient.getUser().getIdUser(),
                                    updatedPatient.getUser().getName(),
                                    updatedPatient.getUser().getEmail(),
                                    updatedPatient.getUser().getRole(),
                                    updatedPatient.getUser().isActive(),
                                    updatedPatient.getUser().getToken()
                            ),
                            updatedPatient.getDoctor() != null ? new DoctorDTO(
                                    updatedPatient.getDoctor().getIdDoctor(),
                                    new UserDTO(
                                            updatedPatient.getDoctor().getUser().getIdUser(),
                                            updatedPatient.getDoctor().getUser().getName(),
                                            updatedPatient.getDoctor().getUser().getEmail(),
                                            updatedPatient.getDoctor().getUser().getRole(),
                                            updatedPatient.getDoctor().getUser().isActive(),
                                            updatedPatient.getDoctor().getUser().getToken()
                                    ),
                                    updatedPatient.getDoctor().getSpecialty(),
                                    updatedPatient.getDoctor().getPhone(),
                                    updatedPatient.getDoctor().isActive()
                            ) : null,
                            updatedPatient.getStratum() != null ? new StratumDTO(
                                    updatedPatient.getStratum().getIdStratum(),
                                    updatedPatient.getStratum().getStratumName(),
                                    updatedPatient.getStratum().getDescription(),
                                    updatedPatient.getStratum().isActive()
                            ) : null,
                            updatedPatient.getAge(),
                            updatedPatient.getGender(),
                            updatedPatient.getWeight(),
                            updatedPatient.getHeight(),
                            updatedPatient.getMedicalConditions(),
                            updatedPatient.isActive(),
                            updatedPatient.getRegistrationDate()
                    );
                }).orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.findById(id)
                .ifPresentOrElse(patient -> {
                    patient.setActive(false);
                    patientRepository.save(patient);
                }, () -> {
                    throw new RuntimeException("Patient not found with ID: " + id);
                });
    }
}
