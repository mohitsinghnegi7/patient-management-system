package com.patientManagementSystem.patientService.controller;

import com.patientManagementSystem.patientService.dto.PatientRequestDTO;
import com.patientManagementSystem.patientService.dto.PatientResponseDTO;
import com.patientManagementSystem.patientService.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients(){
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatients(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
        return ResponseEntity.ok().body(patientService.addPatient(patientRequestDTO));
    }
}
