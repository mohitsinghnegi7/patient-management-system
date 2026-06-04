package com.patientManagementSystem.patientService.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//GRPC Client (PatientService ----> Communicate to --> BillingService)
@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub  blockingStub;  //blocking --> synchronous (jab tkk response nhi aayega current thread wait krega)

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){
        log.info("Connecting to Billing Service GRPC service at {}:{}",serverAddress,serverPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email){
        log.info("Entered createBillingAccount");

        BillingRequest billingRequest = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setEmail(email)
                .setName(name)
                .build();

        BillingResponse billingResponse = blockingStub.createBillingAccount(billingRequest);
        log.info("Exiting createBillingAccount");
        log.info("Received Response from billing service via GROC : {}",billingResponse);
        return billingResponse;
    }
}
