package com.patientManagementSystem.patientService.service;

import com.patientManagementSystem.patientService.dto.PatientRequestDTO;
import com.patientManagementSystem.patientService.dto.PatientResponseDTO;
import com.patientManagementSystem.patientService.exception.EmailAlreadyExistException;
import com.patientManagementSystem.patientService.exception.PatientNotFoundException;
import com.patientManagementSystem.patientService.grpc.BillingServiceGrpcClient;
import com.patientManagementSystem.patientService.kafka.KafkaProducer;
import com.patientManagementSystem.patientService.mapper.PatientMapper;
import com.patientManagementSystem.patientService.model.Patient;
import com.patientManagementSystem.patientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return  patients
                .stream()
                .map(patient -> PatientMapper.toDTO(patient))
                .toList();
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
            throw new EmailAlreadyExistException("Email Already Registered " + patientRequestDTO.getEmail());

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        log.info("Patient saved");

        kafkaProducer.sendMessage(newPatient);


        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());
        log.info("Returned from grpc call");
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException("Patient not found " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
            throw new EmailAlreadyExistException("Email already Exists "+patientRequestDTO.getEmail());

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
