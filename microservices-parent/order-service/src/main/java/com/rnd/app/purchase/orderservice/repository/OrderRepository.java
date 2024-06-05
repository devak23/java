package com.rnd.app.purchase.orderservice.repository;

import com.rnd.app.purchase.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
