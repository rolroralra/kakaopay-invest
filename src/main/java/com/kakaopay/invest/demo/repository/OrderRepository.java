package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    void add(Order order);

    void modify(Order order);

    void remove(Long id);
}
