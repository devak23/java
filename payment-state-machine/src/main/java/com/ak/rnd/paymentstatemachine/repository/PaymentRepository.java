package com.ak.rnd.paymentstatemachine.repository;

import com.ak.rnd.paymentstatemachine.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

// Step 4: Define a repository for storing data

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
