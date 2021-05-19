package com.kakaopay.invest.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Model]투자상품_주문_모델_객체_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public class OrderTest {
    @DisplayName("정상_객체_생성_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001:1001:rolroralra:rolroralra@naver.com:1:1:KaKao:5000000:2000"}, delimiterString = ":")
    void 정상_객체_생성_테스트(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        Order order = new Order(orderId, new User(userId, userName, userEmail), new OrderItem(orderItemId, new Product(productId, productTitle, productAmount), orderItemAmount));

        assertThat(order).isInstanceOfSatisfying(Order.class, order1 -> {
            assertThat(order1.getId())
                    .isGreaterThan(0);

            assertThat(order1.getUser())
                    .isNotNull()
                    .matches(user -> user.getId() > 0);

            assertThat(order1.getItems())
                    .hasSizeGreaterThanOrEqualTo(0)
                    .hasOnlyElementsOfType(OrderItem.class)
                    .allMatch(orderItem -> orderItem.getId() > 0)
                    .allMatch(orderItem -> orderItem.getAmount() > 0)
                    .allMatch(orderItem -> Objects.nonNull(orderItem.getProduct()))
                    .allMatch(orderItem -> orderItem.getProduct().getId() > 0)
                    .allMatch(orderItem -> orderItem.getProduct().getTotalInvestingAmount() > 0);
        });
    }
}
