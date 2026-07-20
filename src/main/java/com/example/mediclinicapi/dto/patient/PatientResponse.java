package com.example.mediclinicapi.dto.patient;

import com.example.mediclinicapi.domain.Patient;

public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        String cpf,
        String phone,
        String email
) {
    public static PatientResponse from(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getCpf(),
                patient.getPhone(),
                patient.getUser().getEmail()
        );
    }
}
