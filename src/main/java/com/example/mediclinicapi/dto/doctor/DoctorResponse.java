package com.example.mediclinicapi.dto.doctor;

import com.example.mediclinicapi.domain.User;
import com.example.mediclinicapi.domain.enums.Role;

public record DoctorResponse(
        Long id,
        String email,
        Role role
) {
    public static DoctorResponse from(User doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getEmail(),
                doctor.getRole()
        );
    }
}
