package com.example.mediclinicapi.service;

import com.example.mediclinicapi.domain.Patient;
import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;
import com.example.mediclinicapi.dto.patient.CreatePatientRequest;
import com.example.mediclinicapi.repository.PatientRepository;
import com.example.mediclinicapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Patient create(CreatePatientRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (patientRepository.findByCpf(request.cpf()).isPresent()) {
            throw new IllegalArgumentException("CPF already registered");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.PATIENT);

        Patient patient = new Patient();
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setCpf(request.cpf());
        patient.setPhone(request.phone());
        patient.setUser(userRepository.save(user));

        return patientRepository.save(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }
}
