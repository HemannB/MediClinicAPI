package com.example.mediclinicapi.repository;

import com.example.mediclinicapi.domain.Appointment;
import com.example.mediclinicapi.domain.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Boolean existsByDoctorIdAndDateTimeAndStatusNot(Long doctorId, LocalDateTime dateTime, AppointmentStatus status);

    Boolean existsByPatientIdAndDateTimeAndStatusNot(Long patientId, LocalDateTime dateTime, AppointmentStatus status);
}
