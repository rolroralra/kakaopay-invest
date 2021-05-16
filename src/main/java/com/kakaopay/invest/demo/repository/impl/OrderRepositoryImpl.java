package com.kakaopay.invest.demo.repository.impl;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl() {
        super();
    }

    @Override
    public List<Order> findAll() {
        return selectAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return selectById(id);
    }

    @Override
    public void add(Order order) {
        insert(order.getId(), order);
    }

    @Override
    public void modify(Order order) {
        update(order.getId(), order);
    }

    @Override
    public void remove(Long id) {
        delete(id);
    }
}
