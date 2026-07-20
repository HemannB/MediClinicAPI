package com.example.mediclinicapi.service;

import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;
import com.example.mediclinicapi.dto.doctor.CreateDoctorRequest;
import com.example.mediclinicapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(CreateDoctorRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User doctor = new User();
        doctor.setEmail(request.email());
        doctor.setPassword(passwordEncoder.encode(request.password()));
        doctor.setRole(Role.DOCTOR);

        return userRepository.save(doctor);
    }

    public List<User> findAll() {
        return userRepository.findByRole(Role.DOCTOR);
    }

    public User findById(Long id) {
        var doctor = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        if (doctor.getRole() != Role.DOCTOR) {
            throw new EntityNotFoundException("Doctor not found");
        }

        return doctor;
    }
}
