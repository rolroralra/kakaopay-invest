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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("[Repository]투자_주문_레퍼지토리_테스트")
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

    @BeforeEach
    void setUp() {
        userRepository.save(new User(1L, "root", "root@kakao.com"));
        userRepository.save(new User(2L, "admin", "admin@kakao.com"));
        userRepository.save(new User(3L, "rolroralra", "rolroralra@naver.com"));
        userRepository.save(new User(4L, "guest", "guest@kakao.com"));

        List<User> userList = userRepository.findAll();

        List<Product> productList = LongStream.range(1, 11)
                .mapToObj(l -> new Product(l, "product" + l, l * 1000, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(l))).collect(Collectors.toList());
        productList.get(1).setState(Product.State.COMPLETED);
        productList.get(1).setFinishedAt(LocalDateTime.now().minusDays(1));
        productList.get(4).setState(Product.State.COMPLETED);
        productList.get(4).setFinishedAt(LocalDateTime.now().minusDays(1));
        productList.get(7).setState(Product.State.COMPLETED);
        productList.get(7).setFinishedAt(LocalDateTime.now().minusDays(1));
        productRepository.saveAll(productList);

        productList = productRepository.findAll();


        orderItemRepository.save(new OrderItem(1L, productList.get(0), 10));
        orderItemRepository.save(new OrderItem(2L, productList.get(1), 20));
        orderItemRepository.save(new OrderItem(3L, productList.get(2), 30));
        orderItemRepository.save(new OrderItem(4L, productList.get(3), 40));


        createAndSaveOrder(1L, 1L, 2L);
        createAndSaveOrder(2L, 3L);
        createAndSaveOrder(3L, 4L);

        System.out.println(orderRepository.findAll());
    }

    @DisplayName("투자상품_주문_전체_목록_조회_테스트")
    @Test
    public void findAll() {
        assertThat(orderRepository.findAll())
            .isNotNull()
            .hasSizeGreaterThanOrEqualTo(0)
            .hasOnlyElementsOfType(Order.class);
    }

    @DisplayName("투자상품_주문_ID_조회_테스트")
    @Test
    public void findById() {
        Long orderId = orderRepository.findAll().stream().mapToLong(Order::getId).findAny().orElse(1L);

        Order order = orderRepository.findById(orderId).orElse(null);
        System.out.println(order);
        assertThat(order)
            .isNotNull()
            .isInstanceOf(Order.class);

        System.out.println(order.getItems());
        assertThat(order.getUser())
                .isNotNull();
        assertThat(order.getItems())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0);

    }

    @DisplayName("투자상품_주문_INSERT_테스트")
    @Test
    public void insert() {
        User user = userRepository.findAll().stream().findAny().orElse(null);
        assertThat(user).isNotNull();

        List<OrderItem> orderItems = orderItemRepository.findAllById(Arrays.asList(1L, 3L));
        assertThat(orderItems)
                .isNotNull()
                .hasSizeGreaterThan(0)
                .hasOnlyElementsOfType(OrderItem.class);

        Order order = new Order(user);
        order.addItem(orderItems);
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

    @DisplayName("투자상품_주문_UPDATE_테스트")
    @Test
    public void update() {
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

    @DisplayName("투자상품_주문_DELETE_테스트")
    @Test
    public void delete() {
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

    private Order createAndSaveOrder(Long userId, Long... orderItemIds) {
        Order order = new Order();
        User user = userRepository.findById(userId).orElse(null);
        assertThat(user).isNotNull();

        List<OrderItem> orderItems = orderItemRepository.findAllById(Arrays.asList(orderItemIds));
        order.setUser(user);
        for (OrderItem orderItem : orderItems) {
            order.addItem(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return order;
    }

}