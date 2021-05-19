package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(Long productId, Long amount);

    List<OrderItem> findOrderItemsByUserId(Long userId);
}
