package com.example.mediclinicapi.controller;

import com.example.mediclinicapi.dto.patient.CreatePatientRequest;
import com.example.mediclinicapi.dto.patient.PatientResponse;
import com.example.mediclinicapi.service.PatientService;
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
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody @Valid CreatePatientRequest request) {
        var patient = patientService.create(request);

        return ResponseEntity
                .created(URI.create("/patients/" + patient.getId()))
                .body(PatientResponse.from(patient));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        var patients = patientService.findAll().stream()
                .map(PatientResponse::from)
                .toList();

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(PatientResponse.from(patientService.findById(id)));
    }
}
