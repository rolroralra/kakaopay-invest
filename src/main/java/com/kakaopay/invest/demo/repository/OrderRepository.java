package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    default void add(Order order) {
        save(order);
    }

    default void modify(Order order) {
        save(order);
    }

    default void remove(Long id) {
        deleteById(id);
    }
}
