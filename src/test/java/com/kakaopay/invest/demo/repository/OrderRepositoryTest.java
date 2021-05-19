package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("[Repository]투자_주문_레퍼지토리_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
class OrderRepositoryTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @DisplayName("테스트01_정상_투자상품_주문_전체_목록_조회")
    @Test
    public void 테스트01_정상_투자상품_주문_전체_목록_조회() {
        assertThat(orderRepository.findAll())
            .isNotNull()
            .hasSizeGreaterThanOrEqualTo(0)
            .hasOnlyElementsOfType(Order.class);
    }

    @DisplayName("테스트02_정상_특정_ID_투자상품_주문_조회_테스트")
    @Test
    public void 테스트02_정상_특정_ID_투자상품_주문_조회_테스트() {
        Long orderId = orderRepository.findAll().stream().mapToLong(Order::getId).findAny().orElse(1L);

        Order order = orderRepository.findById(orderId).orElse(null);
        System.out.println(order);
        assertThat(order)
            .isNotNull()
            .isInstanceOf(Order.class);

//        System.out.println(order.getItems());
        assertThat(order.getUser())
                .isNotNull();
        assertThat(order.getItems())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0);

    }

    @DisplayName("테스트03_정상_투자상품_주문_INSERT")
    @Test
    public void 테스트03_정상_투자상품_주문_INSERT() {
        User user = userRepository.findAll().stream().findAny().orElse(null);
        assertThat(user).isNotNull();

        List<OrderItem> orderItems = orderItemRepository.findAllById(Arrays.asList(1L, 3L));
        assertThat(orderItems)
                .isNotNull()
                .hasSizeGreaterThan(0)
                .hasOnlyElementsOfType(OrderItem.class);

        Order order = new Order(user);
        order.addItem(orderItems);
        order.complete();
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        Order searchResult = orderRepository.findById(order.getId()).orElse(null);
        assertThat(searchResult)
                .isNotNull()
                .isEqualTo(order);
        assertThat(searchResult.getItems())
                .isNotNull()
                .containsAll(order.getItems());
    }

    @DisplayName("테스트04_정상_투자상품_주문_UPDATE")
    @Test
    public void 테스트04_정상_투자상품_주문_UPDATE() {
        Order order = orderRepository.findAll().stream().findAny().orElse(null);
        assertThat(order).isNotNull();

        OrderItem orderItem = orderItemRepository.findAll().stream().filter(item -> !order.getItems().contains(item)).findAny().orElse(null);
        assertThat(orderItem).isNotNull();

        order.addItem(orderItem);
        order.setFinishedAt(LocalDateTime.now());
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        assertThat(orderRepository.findById(order.getId()))
                .isNotEmpty()
                .hasValue(order);
    }

    @DisplayName("테스트05_정상_투자상품_주문_DELETE")
    @Test
    public void 테스트05_정상_투자상품_주문_DELETE() {
        Order order = orderRepository.findAll().stream().findAny().orElse(null);
        assertThat(order).isNotNull();

        orderRepository.deleteById(order.getId());

        assertThat(orderRepository.findById(order.getId()))
                .isEmpty();
        assertThat(orderRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Order.class)
                .doesNotContain(order);
    }
}