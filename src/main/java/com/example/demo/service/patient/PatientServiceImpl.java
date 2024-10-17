package com.example.demo.service.patient;

import com.example.demo.dao.doctor.Doctor;
import com.example.demo.dao.doctor.DoctorRepository;
import com.example.demo.dao.doctor.dto.DoctorDTO;
import com.example.demo.dao.patient.Patient;
import com.example.demo.dao.patient.PatientRepository;
import com.example.demo.dao.patient.dto.*;
import com.example.demo.dao.stratum.Stratum;
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
import com.example.demo.dao.recommendation.Recommendation;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(this::toPatientDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findPatientById(Long id) {
        return patientRepository.findByIdPatient(id)
                .map(this::toPatientDTO);
    }

    @Override
    public PatientDTO createPatient(CreatePatientDTO request) {
        User user = userService.createUser(new CreateUserDTO(
                request.username(),
                request.email(),
                request.password(),
                Role.PATIENT,
                null
        ));

        Patient patient = new Patient();
        patient.setUser(user);
        setPatientDetails(patient, request);
        patient = patientRepository.save(patient);

        return toPatientDTO(patient);
    }

    @Override
    public PatientDTO updatePatient(Long id, UpdatePatientDTO request) {
        return patientRepository.findById(id)
                .map(patient -> {
                    updateDoctor(request, patient);
                    updateStratum(request, patient);
                    updatePatientDetails(request, patient);

                    User user = patient.getUser();
                    if (user != null) {
                        Optional.ofNullable(request.username()).ifPresent(user::setName);
                        Optional.ofNullable(request.password()).ifPresent(user::setPassword);
                    }

                    // Guardar el paciente actualizado
                    Patient updatedPatient = patientRepository.save(patient);
                    return toPatientDTO(updatedPatient);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));
    }

    private void updateDoctor(UpdatePatientDTO request, Patient patient) {
        Optional.ofNullable(request.idDoctor()).flatMap(doctorRepository::findById).ifPresent(patient::setDoctor);
    }

    private void updateStratum(UpdatePatientDTO request, Patient patient) {
        Optional.ofNullable(request.idStratum()).flatMap(stratumRepository::findById).ifPresent(patient::setStratum);
    }

    private void updatePatientDetails(UpdatePatientDTO request, Patient patient) {
        Optional.of(request.age())
                .filter(age -> age >= 0)
                .ifPresent(patient::setAge);
        Optional.of(request.gender())
                .ifPresent(patient::setGender);
        Optional.of(request.weight())
                .filter(weight -> weight >= 0)
                .ifPresent(patient::setWeight);
        Optional.of(request.height())
                .filter(height -> height >= 0)
                .ifPresent(patient::setHeight);
        Optional.ofNullable(request.medicalConditions())
                .ifPresent(patient::setMedicalConditions);
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

    private void setPatientDetails(Patient patient, CreatePatientDTO request) {
        patient.setAge(request.age());
        patient.setGender(request.gender());
        patient.setWeight(request.weight());
        patient.setHeight(request.height());
        patient.setRegistrationDate(new java.util.Date());
        patient.setActive(true);
    }

    private PatientDTO toPatientDTO(Patient patient) {
        return new PatientDTO(
                patient.getIdPatient(),
                toUserDTO(patient.getUser()),
                toDoctorDTO(patient.getDoctor()),
                toStratumDTO(patient.getStratum()),
                patient.getAge(),
                patient.getGender(),
                patient.getWeight(),
                patient.getHeight(),
                patient.getMedicalConditions(),
                patient.isActive(),
                patient.getRegistrationDate()
        );
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getToken()
        );
    }

    private DoctorDTO toDoctorDTO(Doctor doctor) {
        if (doctor == null) return null;
        return new DoctorDTO(
                doctor.getIdDoctor(),
                toUserDTO(doctor.getUser()),
                doctor.getSpecialty(),
                doctor.getPhone(),
                doctor.isActive()
        );
    }

    private StratumDTO toStratumDTO(Stratum stratum) {
        if (stratum == null) return null;

        List<String> recommendationDescriptions = Optional.ofNullable(stratum.getRecommendations())
                .orElseGet(HashSet::new)
                .stream()
                .map(Recommendation::getContent)
                .collect(Collectors.toList());

        return new StratumDTO(
                stratum.getIdStratum(),
                stratum.getStratumName(),
                stratum.getDescription(),
                stratum.isActive(),
                recommendationDescriptions
        );
    }
}
