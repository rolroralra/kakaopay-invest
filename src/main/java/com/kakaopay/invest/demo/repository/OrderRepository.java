package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
