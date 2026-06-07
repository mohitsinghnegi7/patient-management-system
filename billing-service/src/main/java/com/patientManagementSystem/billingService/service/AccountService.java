package com.patientManagementSystem.billingService.service;


import com.patientManagementSystem.billingService.model.Account;
import com.patientManagementSystem.billingService.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(String patientId){
        Account account = new Account();
        account.setPatientId(patientId);
        account.setStatus("ACTIVE");
        account.setTotalAmount(0.0);

        return accountRepository.save(account);
    }
}
