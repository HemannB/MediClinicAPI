package com.example.mediclinicapi.service;

import com.example.mediclinicapi.domain.Appointment;
import com.example.mediclinicapi.domain.enums.AppointmentStatus;
import com.example.mediclinicapi.domain.enums.Role;
import com.example.mediclinicapi.dto.appointment.CreateAppointmentRequest;
import com.example.mediclinicapi.repository.AppointmentRepository;
import com.example.mediclinicapi.repository.PatientRepository;
import com.example.mediclinicapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment create(CreateAppointmentRequest request) {
        var doctor = userRepository.findById(request.doctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        if (doctor.getRole() != Role.DOCTOR) {
            throw new EntityNotFoundException("Doctor not found");
        }

        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        if (appointmentRepository.existsByDoctorIdAndDateTimeAndStatusNot(
                doctor.getId(), request.dateTime(), AppointmentStatus.CANCELLED)) {
            throw new IllegalArgumentException("Doctor already has an appointment at this time");
        }

        if (appointmentRepository.existsByPatientIdAndDateTimeAndStatusNot(
                patient.getId(), request.dateTime(), AppointmentStatus.CANCELLED)) {
            throw new IllegalArgumentException("Patient already has an appointment at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(request.dateTime());
        appointment.setStatus(AppointmentStatus.APPROVED);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }
}
