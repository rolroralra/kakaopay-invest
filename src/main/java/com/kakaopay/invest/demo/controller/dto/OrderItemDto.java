package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Long amount;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private ProductDto product;

    public OrderItemDto(OrderItem source) {
        this.setAmount(source.getAmount());
        this.setId(source.getProduct().getId());
        this.setStartedAt(source.getOrder().getStartedAt());
        this.setFinishedAt(source.getOrder().getFinishedAt());
        this.setProduct(new ProductDto(source.getProduct()));
    }
}
