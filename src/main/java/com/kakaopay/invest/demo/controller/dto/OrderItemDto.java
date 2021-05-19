package com.kakaopay.invest.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakaopay.invest.demo.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItemDto {
    private final Long id;
    private final Long amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime finishedAt;
    private final ProductDto product;

    public OrderItemDto(OrderItem source) {
        id = source.getId();
        amount = source.getAmount();
        startedAt = source.getOrder().getStartedAt();
        finishedAt = source.getOrder().getFinishedAt();
        product = new ProductDto(source.getProduct());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItemDto{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
