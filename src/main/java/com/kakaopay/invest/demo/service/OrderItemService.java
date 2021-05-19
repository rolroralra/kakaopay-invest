package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(Long productId, Long amount);

    List<OrderItem> findOrderItemsByUserId(Long userId);

    Page<OrderItem> findOrderItemsByUserId(Long userId, Pageable pageable);
}
