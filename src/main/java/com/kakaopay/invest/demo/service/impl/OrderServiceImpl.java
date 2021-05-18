package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.repository.OrderRepository;
import com.kakaopay.invest.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Order takeOrder(User user, OrderItem orderItem) {
        Order order = new Order(user, orderItem);
        orderRepository.save(order);
        return order;
    }
}
