package com.example.mediclinicapi.repository;

import com.example.mediclinicapi.domain.Appointment;
import com.example.mediclinicapi.domain.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateTimeAndStatusNot(Long doctorId, LocalDateTime dateTime, AppointmentStatus status);

    boolean existsByPatientIdAndDateTimeAndStatusNot(Long patientId, LocalDateTime dateTime, AppointmentStatus status);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);
}
