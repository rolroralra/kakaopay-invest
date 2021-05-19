package com.kakaopay.invest.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakaopay.invest.demo.model.Order;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;
    private Order.State state;

    public OrderDto(Order source) {
        BeanUtils.copyProperties(source, this);
        setUser(new UserDto(source.getUser()));
        setItems(source.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", items=").append(items);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
