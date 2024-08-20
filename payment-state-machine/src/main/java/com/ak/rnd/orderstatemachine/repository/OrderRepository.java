package com.ak.rnd.orderstatemachine.repository;

import com.ak.rnd.orderstatemachine.model.OrderInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderInvoice, Long> {
}
