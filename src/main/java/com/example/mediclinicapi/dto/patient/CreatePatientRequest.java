package com.example.mediclinicapi.dto.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreatePatientRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF must contain 11 digits")
        String cpf,

        String phone,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {
}
