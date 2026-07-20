package com.example.mediclinicapi.controller;

import com.example.mediclinicapi.dto.doctor.CreateDoctorRequest;
import com.example.mediclinicapi.dto.doctor.DoctorResponse;
import com.example.mediclinicapi.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponse> create(@RequestBody @Valid CreateDoctorRequest request) {
        var doctor = doctorService.create(request);

        return ResponseEntity
                .created(URI.create("/doctors/" + doctor.getId()))
                .body(DoctorResponse.from(doctor));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> findAll() {
        var doctors = doctorService.findAll().stream()
                .map(DoctorResponse::from)
                .toList();

        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(DoctorResponse.from(doctorService.findById(id)));
    }
}
