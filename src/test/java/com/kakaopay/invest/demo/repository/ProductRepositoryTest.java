package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Repository]투자상품_레퍼지토리_테스트")
@SpringBootTest
public class ProductRepositoryTest {
    private final ProductRepository productRepository;
    private List<Product> productList;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @DisplayName("투자상품_전체_목록_조회_테스트")
    @Test
    public void findAll() {
        productList = productRepository.findAll();
        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class);
    }

    @DisplayName("모집상태를_통한_투자상품_목록_조회_테스트")
    @Test
    public void findByState() {
        productList = productRepository.findByState(Product.State.PROCEEDING);
        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("모집중인_투자상품_목록_조회_테스트")
    @Test
    public void findProceedingInvestmentProducts() {
        productList = productRepository.findProceedingInvestmentProducts();
        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("신규_투자상품_INSERT_테스트")
    @Test
    public void add() {
        Product product = new Product(17L, "Test-17", 24000L, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(3));
        productRepository.add(product);
        productList = productRepository.findAll();

        assertThat(productList)
                .isNotNull()
                .hasOnlyElementsOfType(Product.class)
                .contains(product);
    }

    @DisplayName("기존_투자상품_UPDATE_테스트")
    @Test
    public void modify() {
        Product updatedProduct = getAnyInvestmentProduct();

        updatedProduct.setTitle("Changed Title Test");
        updatedProduct.setStartedAt(updatedProduct.getStartedAt().minusDays(2));
        updatedProduct.setFinishedAt(updatedProduct.getFinishedAt().plusDays(1));
        productRepository.modify(updatedProduct);

        Optional<Product> searchResult = productRepository.findById(updatedProduct.getId());

        assertThat(searchResult)
                .satisfies(Optional::isPresent)
                .hasValue(updatedProduct);

        assertThat(productRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .contains(updatedProduct);
    }

    @DisplayName("기존_투자상품_DELETE_테스트")
    @Test
    public void remove() {
        Product deletedProduct = getAnyInvestmentProduct();

        productRepository.remove(deletedProduct.getId());

        Optional<Product> searchResult = productRepository.findById(deletedProduct.getId());
        assertThat(searchResult.isEmpty()).isTrue();

        assertThat(productRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .doesNotContain(deletedProduct);
    }

    private Product getAnyInvestmentProduct() {
        Product product = productRepository.findAll().stream().findAny().orElse(null);
        assertThat(product).isNotNull();
        return product;
    }
}
