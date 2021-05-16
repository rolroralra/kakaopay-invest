package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("[Repository]투자상품_주문_레퍼지토리_테스트")
@SpringBootTest
class OrderRepositoryTest {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @DisplayName("투자상품_주문_전체_목록_조회_테스트")
    @Test
    void findAll() {
        assertThat(orderRepository.findAll())
            .isNotNull()
            .hasSizeGreaterThanOrEqualTo(0)
            .hasOnlyElementsOfType(Order.class);
    }

    @DisplayName("투자상품_주문_ID_조회_테스트")
    @Test
    void findById() {
        orderRepository.add(new Order(1L, new User(1001L, "rolroralra", "rolroralra@gmail.com"), new OrderItem(10001L, new Product(500101L, "KaKao", 5000000L), 3000L)));
        orderRepository.findById(1L);
    }

    @DisplayName("투자상품_주문_INSERT_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001:1001:rolroralra:rolroralra@naver.com:1:1:KaKao:5000000:2000"}, delimiterString = ":")
    void add(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        int beforeSize = orderRepository.findAll().size();

        final Order order = addOrder(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);

//        final Order wanted = Order.of(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);
        final Order wanted = copyOrder(order);

        wanted.setStartedAt(order.getStartedAt());
        wanted.setItems(new ArrayList<>(order.getItems()));
        wanted.setFinishedAt(order.getFinishedAt());

        assertThat(orderRepository.findById(orderId))
                .isNotEmpty()
                .hasValue(wanted);

        addOrder(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);

        int afterSize = orderRepository.findAll().size();

        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @DisplayName("투자상품_주문_UPDATE_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001:1001:rolroralra:rolroralra@naver.com:1:1:KaKao:5000000:2000"}, delimiterString = ":")
    void modify(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        final Order addedOrder = addOrder(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);

        Order updatedOrder = orderRepository.findById(orderId).orElse(null);

        assertThat(updatedOrder)
                .isNotNull()
                .isEqualTo(addedOrder);

        updatedOrder.complete();

        orderRepository.modify(updatedOrder);

        Order wanted = copyOrder(updatedOrder);

        assertThat(orderRepository.findById(orderId).orElse(null))
                .isNotNull()
                .isEqualTo(wanted);

    }

    @DisplayName("투자상품_주문_DELETE_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001:1001:rolroralra:rolroralra@naver.com:1:1:KaKao:5000000:2000"}, delimiterString = ":")
    void remove(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        final Order order = addOrder(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);

        int beforeSize = orderRepository.findAll().size();

        orderRepository.remove(orderId);

        int afterSize = orderRepository.findAll().size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
        assertThat(orderRepository.findById(orderId)).isEmpty();
        assertThat(orderRepository.findAll()).doesNotContain(order);
    }

//    private Product getProductById(long id) {
//        return productRepository.findById(id)
//                .orElse(
//                        new Product(
//                        1L,
//                        "Test Prodcut",
//                        10000L)
//                );
//    }
//
//    private OrderItem createOrderItem(long orderId, long productId, long amount) {
//        return new OrderItem(orderId, getProductById(productId), amount);
//    }

    private Order addOrder(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        final Order order = Order.of(orderId, userId, userName, userEmail, orderItemId, productId, productTitle, productAmount, orderItemAmount);

        orderRepository.add(order);

        return order;
    }

    private Order copyOrder(Order order) {
        try {
            return Order.of(order);
        } catch (CloneNotSupportedException e) {
            Assertions.fail(e.getMessage());
            return null;
        }
    }
}