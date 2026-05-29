package com.patientManagementSystem.patientService.service;

import com.patientManagementSystem.patientService.dto.PatientRequestDTO;
import com.patientManagementSystem.patientService.dto.PatientResponseDTO;
import com.patientManagementSystem.patientService.mapper.PatientMapper;
import com.patientManagementSystem.patientService.model.Patient;
import com.patientManagementSystem.patientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return  patients
                .stream()
                .map(patient -> PatientMapper.toDTO(patient))
                .toList();
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }
}
