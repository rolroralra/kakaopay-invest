package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]투자_주문상품_레퍼지토리_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
public class OrderItemRepositoryTest {
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @DisplayName("테스트01_정상_주문상품_전체_목록_조회")
    @Test
    void 테스트01_정상_주문상품_전체_목록_조회() {
        assertThat(orderItemRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(OrderItem.class);
    }

    @DisplayName("테스트02_정상_특정_ID_투자상품_주문_조회")
    @Test
    void 테스트02_정상_특정_ID_투자상품_주문_조회() {
        assertThat(orderItemRepository.findById(1L).orElse(null))
                .isNotNull()
                .isInstanceOf(OrderItem.class);
    }

    @DisplayName("테스트03_정상_사용자_ID_통한_투자상품_주문_목록_조회")
    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6})
    void 테스트03_정상_사용자_ID_통한_투자상품_주문_목록_조회(Long userId) {
        assertThat(orderItemRepository.findByUser(userId))
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(OrderItem.class);
    }

    @DisplayName("테스트04_정상_주문상품_INSERT")
    @Test
    public void 테스트04_정상_주문상품_INSERT() {
        Product product = productRepository.findByState(Product.State.PROCEED).stream().findAny().orElse(null);
        assertThat(product).isNotNull();

        Order order = orderRepository.findAll().stream().findAny().orElse(null);
        assertThat(order).isNotNull();

        OrderItem orderItem = new OrderItem(product, 10L);
        order.addItem(orderItem);
        orderItemRepository.save(orderItem);

        assertThat(orderItemRepository.findById(orderItem.getId()))
                .isNotEmpty()
                .hasValue(orderItem);
    }



    @DisplayName("테스트05_정상_주문상품_UPDATE_테스트")
    @Test
    public void 테스트05_정상_주문상품_UPDATE_테스트() {
        OrderItem orderItem = orderItemRepository.findAll().stream().findAny().orElse(null);
        assertThat(orderItem).isNotNull();

        orderItem.setAmount(orderItem.getAmount() + 10);
        orderItemRepository.save(orderItem);

        assertThat(orderItemRepository.findById(orderItem.getId()))
                .isNotEmpty()
                .hasValue(orderItem);
    }

    @DisplayName("테스트06_정상_주문상품_DELETE_테스트")
    @Test
    public void 테스트06_정상_주문상품_DELETE_테스트() {
        Product product = productRepository.findByState(Product.State.PROCEED).stream().findAny().orElse(null);
        assertThat(product).isNotNull();

        OrderItem orderItem = new OrderItem(product, 10L);
        orderItemRepository.save(orderItem);

        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getId()).isNotNull();

        orderItemRepository.deleteById(orderItem.getId());

        assertThat(orderItemRepository.findById(orderItem.getId()))
                .isEmpty();

        assertThat(orderItemRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(OrderItem.class)
                .doesNotContain(orderItem);
    }


}
