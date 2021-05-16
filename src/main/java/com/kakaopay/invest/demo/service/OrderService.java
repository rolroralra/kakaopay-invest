package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Order;

public interface OrderService {
    Order takeOrder(Long userId, Long productId, Long amount);
}
