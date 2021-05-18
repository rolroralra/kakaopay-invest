package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Product product;
    private Long orderId;
    private Long amount;
    private OrderItem.State state;

    protected OrderItemDto() {
    }

    public OrderItemDto(OrderItem source) {
        BeanUtils.copyProperties(source, this);
        this.setOrderId(source.getOrder().getId());
    }
}
