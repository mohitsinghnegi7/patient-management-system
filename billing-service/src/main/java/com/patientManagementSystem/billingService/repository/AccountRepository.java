package com.patientManagementSystem.billingService.repository;

import com.patientManagementSystem.billingService.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
