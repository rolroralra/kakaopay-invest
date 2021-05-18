package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
