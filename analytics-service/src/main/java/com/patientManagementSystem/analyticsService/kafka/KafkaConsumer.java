package com.patientManagementSystem.analyticsService.kafka;

import com.patientManagementSystem.analyticsService.dto.PatientProducerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeMessage(PatientProducerDTO patientConsumerDTO){
        log.info("Patient Cosumer consumer this message : {}",patientConsumerDTO);
    }
}
