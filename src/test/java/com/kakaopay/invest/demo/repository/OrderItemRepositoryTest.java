package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]투자_주문상품_레퍼지토리_테스트")
@SpringBootTest
public class OrderItemRepositoryTest {
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @DisplayName("주문상품_전체_목록_조회_테스트")
    @Test
    void findAll() {
        assertThat(orderItemRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(OrderItem.class);
    }

    @DisplayName("투자상품_주문_ID_조회_테스트")
    @Test
    void findById() {
        assertThat(orderItemRepository.findById(1L).orElse(null))
                .isNotNull()
                .isInstanceOf(OrderItem.class);
    }

    @DisplayName("주문상품_INSERT_테스트")
    @Test
    public void insert() {
        Product product = productRepository.findProceedingInvestmentProducts().stream().findAny().orElse(null);
        assertThat(product).isNotNull();

        OrderItem orderItem = new OrderItem(product, 10L);
        orderItemRepository.save(orderItem);
        assertThat(orderItemRepository.findById(orderItem.getId()))
                .isNotEmpty()
                .hasValue(orderItem);
    }

    @DisplayName("주문상품_UPDATE_테스트")
    @Test
    public void update() {
        OrderItem orderItem = orderItemRepository.findAll().stream().findAny().orElse(null);
        assertThat(orderItem).isNotNull();

        orderItem.setAmount(orderItem.getAmount() + 10);
        orderItemRepository.save(orderItem);

        assertThat(orderItemRepository.findById(orderItem.getId()))
                .isNotEmpty()
                .hasValue(orderItem);
    }

    @DisplayName("주문상품_DELETE_테스트")
    @Test
    public void delete() {
        Product product = productRepository.findProceedingInvestmentProducts().stream().findAny().orElse(null);
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
