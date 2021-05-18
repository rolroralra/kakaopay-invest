package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.User;

public interface OrderService {
    Order takeOrder(User user, OrderItem orderItem);
}
