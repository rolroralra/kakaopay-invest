package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]투자상품_레퍼지토리_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
public class ProductRepositoryTest {
    private final ProductRepository productRepository;
    private List<Product> productList;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
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
    }

    @DisplayName("테스트01_정상_투자상품_전체_목록_조회_테스트")
    @Test
    public void 테스트01_정상_투자상품_전체_목록_조회_테스트() {
        productList = productRepository.findAll();
        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class);

        System.out.println(productList);
    }

    @DisplayName("테스트02_정상_투자상품_전체_목록_조회_테스트")
    @Test
    public void 테스트02_정상_투자상품_전체_목록_조회_테스트() {
        productList = productRepository.findByState(Product.State.PROCEED);
        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("테스트03_정상_모집중인_투자상품_목록_조회")
    @Test
    public void 테스트03_정상_모집중인_투자상품_목록_조회() {
        productList = productRepository.findByState(Product.State.PROCEED);
        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("테스트04_정상_신규_투자상품_INSERT")
    @Test
    public void 테스트04_정상_신규_투자상품_INSERT() {
        long oldCount = productRepository.count();
        Product product = new Product(null, "TestProductTitle", 24000L, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(3));
        productRepository.save(product);
        productList = productRepository.findAll();

        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class);
        assertThat(productList.size()).isEqualTo(oldCount + 1);
    }

    @DisplayName("테스트04_정상_기존_투자상품_UPDATE")
    @Test
    public void 테스트04_정상_기존_투자상품_UPDATE() {
        Product updatedProduct = getAnyInvestmentProduct();

        updatedProduct.setTitle("Changed Title Test");
        updatedProduct.setStartedAt(updatedProduct.getStartedAt().minusDays(2));
        updatedProduct.setFinishedAt(updatedProduct.getFinishedAt().plusDays(1));
        productRepository.save(updatedProduct);

        Optional<Product> searchResult = productRepository.findById(updatedProduct.getId());

        assertThat(searchResult)
                .isNotEmpty()
                .hasValue(updatedProduct);

        assertThat(productRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .contains(updatedProduct);
    }

    @DisplayName("테스트05_정상_기존_투자상품_DELETE")
    @Test
    public void 테스트05_정상_기존_투자상품_DELETE() {
        Product product = new Product(null, "new Product", 100L);
        productRepository.save(product);
        System.out.println(product);

        productRepository.deleteById(product.getId());

        Optional<Product> searchResult = productRepository.findById(product.getId());
        assertThat(searchResult.isEmpty()).isTrue();

        assertThat(productRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .doesNotContain(product);
    }

    private Product getAnyInvestmentProduct() {
        Product product = productRepository.findAll().stream().findAny().orElse(null);
        assertThat(product).isNotNull();
        return product;
    }
}
