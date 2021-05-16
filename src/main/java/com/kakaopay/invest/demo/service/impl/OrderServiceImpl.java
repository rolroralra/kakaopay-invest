package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.repository.OrderRepository;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.repository.UserRepository;
import com.kakaopay.invest.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order takeOrder(Long userId, Long productId, Long amount) {
        Order order = new Order(1L, userRepository.findById(userId).orElse(null), new OrderItem(1L, productRepository.findById(productId).orElse(null), amount));
        orderRepository.add(order);
        return order;
    }
}
