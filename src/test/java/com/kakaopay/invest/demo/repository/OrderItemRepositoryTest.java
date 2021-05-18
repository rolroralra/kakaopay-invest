package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

    @BeforeEach
    void setUp() {
        List<Product> productList = LongStream.range(1, 11)
                .mapToObj(l -> new Product(l, "product" + l, l * 1000, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(l))).collect(Collectors.toList());

        productList.get(1).setState(Product.State.COMPLETED);
        productList.get(1).setFinishedAt(LocalDateTime.now().minusDays(1));

        productList.get(4).setState(Product.State.COMPLETED);
        productList.get(4).setFinishedAt(LocalDateTime.now().minusDays(1));

        productList.get(7).setState(Product.State.COMPLETED);
        productList.get(7).setFinishedAt(LocalDateTime.now().minusDays(1));

        productRepository.saveAll(productList);

        orderItemRepository.save(new OrderItem(1L, productList.get(0), 10));
        orderItemRepository.save(new OrderItem(2L, productList.get(1), 20));
        orderItemRepository.save(new OrderItem(3L, productList.get(2), 30));
        orderItemRepository.save(new OrderItem(4L, productList.get(3), 40));
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

    @DisplayName("사용자_DELETE_테스트")
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
