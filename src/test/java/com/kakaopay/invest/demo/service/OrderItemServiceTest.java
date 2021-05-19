package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("[Service]투자상품_주문_서비스_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
class OrderItemServiceTest {
    private OrderItemService orderItemService;

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Test
    void createOrderItem() {
    }

    @Test
    void findOrderItems() {
        for (OrderItem orderItem : orderItemService.findOrderItemsByUserId(2L)) {
            System.out.println(orderItem);
        }
    }
}