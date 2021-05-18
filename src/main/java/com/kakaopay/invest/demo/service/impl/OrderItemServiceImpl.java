package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.repository.OrderItemRepository;
import com.kakaopay.invest.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public OrderItem createOrderItem(Product product, Long amount) {
        OrderItem orderItem = new OrderItem(product, amount);

        orderItemRepository.save(orderItem);

        return orderItem;
    }
}
