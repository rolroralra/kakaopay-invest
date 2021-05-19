package com.kakaopay.invest.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Model]주문_투자상품_목록_모델_객체_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public class OrderItemTest {
    @DisplayName("정상_객체_생성_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001:1001:KaKao:5000000:2000"}, delimiterString = ":")
    void test(long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        OrderItem orderItem = new OrderItem(orderItemId, new Product(productId, productTitle, productAmount), orderItemAmount);

        assertThat(orderItem)
                .isNotNull()
                .isInstanceOf(OrderItem.class)
                .matches(item -> item.getId() > 0)
                .matches(item -> item.getAmount() > 0)
                .matches(item -> Objects.nonNull(item.getProduct()));
    }
}
