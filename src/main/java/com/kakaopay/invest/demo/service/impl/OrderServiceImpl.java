package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.repository.OrderItemRepository;
import com.kakaopay.invest.demo.repository.OrderRepository;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public Order takeOrder(User user, OrderItem orderItem) {
        Order order = new Order(user, orderItem);
        order.complete();

        orderRepository.save(order);
        orderItemRepository.save(orderItem);
        productRepository.save(orderItem.getProduct());

        return order;
    }
}
