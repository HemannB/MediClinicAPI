package com.example.mediclinicapi.controller;

import com.example.mediclinicapi.domain.Appointment;
import com.example.mediclinicapi.dto.appointment.AppointmentResponse;
import com.example.mediclinicapi.dto.appointment.CompleteAppointmentRequest;
import com.example.mediclinicapi.dto.appointment.CreateAppointmentRequest;
import com.example.mediclinicapi.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody @Valid CreateAppointmentRequest request) {
        var appointment = appointmentService.create(request);

        return ResponseEntity
                .created(URI.create("/appointments/" + appointment.getId()))
                .body(AppointmentResponse.from(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long patientId
    ) {
        var appointments = findAppointments(doctorId, patientId).stream()
                .map(AppointmentResponse::from)
                .toList();

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AppointmentResponse.from(appointmentService.findById(id)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(AppointmentResponse.from(appointmentService.cancel(id)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponse> complete(
            @PathVariable Long id,
            @RequestBody CompleteAppointmentRequest request
    ) {
        return ResponseEntity.ok(AppointmentResponse.from(appointmentService.complete(id, request)));
    }

    private List<Appointment> findAppointments(Long doctorId, Long patientId) {
        if (doctorId != null && patientId != null) {
            throw new IllegalArgumentException("Use only one appointment filter at a time");
        }

        if (doctorId != null) {
            return appointmentService.findByDoctorId(doctorId);
        }

        if (patientId != null) {
            return appointmentService.findByPatientId(patientId);
        }

        return appointmentService.findAll();
    }
}
