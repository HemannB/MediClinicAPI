package com.example.mediclinicapi.dto.appointment;

import com.example.mediclinicapi.domain.Appointment;
import com.example.mediclinicapi.domain.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long doctorId,
        String doctorEmail,
        Long patientId,
        String patientName,
        LocalDateTime dateTime,
        AppointmentStatus status,
        String medicalNotes
) {
    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getEmail(),
                appointment.getPatient().getId(),
                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName(),
                appointment.getDateTime(),
                appointment.getStatus(),
                appointment.getMedicalNotes()
        );
    }
}
