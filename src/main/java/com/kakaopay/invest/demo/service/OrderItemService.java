package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;

public interface OrderItemService {
    OrderItem createOrderItem(Product product, Long amount);
}
