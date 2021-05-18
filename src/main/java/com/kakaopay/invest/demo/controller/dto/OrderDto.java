package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private UserDto user;
    private List<OrderItemDto> items;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Order.State state;

    protected OrderDto() {
    }

    public OrderDto(Order source) {
        BeanUtils.copyProperties(source, this);
        setUser(new UserDto(source.getUser()));
        setItems(source.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList()));
    }
}
