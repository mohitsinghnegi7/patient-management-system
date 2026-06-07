package com.patientManagementSystem.billingService.grpc;

import billing.BillingResponse;
import com.patientManagementSystem.billingService.model.Account;
import com.patientManagementSystem.billingService.service.AccountService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class BillingGrpcService extends BillingServiceImplBase {

    private final AccountService accountService;

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<billing.BillingResponse> responseStreamObserver){
        log.info("CreateBillingAccount request received {}",billingRequest.toString());

        Account account = accountService.createAccount(billingRequest.getPatientId());

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(account.getId().toString())
                .setStatus(account.getStatus())
                .setPatientId(account.getPatientId())
                .setTotalAmount(account.getTotalAmount())
                .build();

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
