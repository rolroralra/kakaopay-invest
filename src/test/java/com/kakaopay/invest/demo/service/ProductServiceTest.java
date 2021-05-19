package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.UserProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Service]투자상품_서비스_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
public class ProductServiceTest {
    private ProductService productService;

    @Autowired
    public void setInvestmentProductService(ProductService productService) {
        this.productService = productService;
    }

    private Product findAny() {
        return productService.findAll().stream().findAny().orElse(null);
    }

    @DisplayName("테스트01_정상_투자모집상태에_따른_투자상품_목록_조회")
    @Test
    void 테스트01_정상_투자모집상태에_따른_투자상품_목록_조회() {
        List<Product> productList = productService.findByState(Product.State.PROCEED);

        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class);
    }

    @DisplayName("테스트02_정상_모집중인_투자상품_목록_조회")
    @Test
    void 테스트02_정상_모집중인_투자상품_목록_조회() {
        List<Product> productList = productService.findProceedingInvestProducts();
        for (Product product : productList) {
            System.out.println(product);
        }

        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("테스트03_정상_사용자_투자상품_전체목록_조회")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    void 테스트03_정상_사용자_투자상품_전체목록_조회(Long userId) {
        List<UserProduct> userProductList = productService.findUserProductsByUserId(userId);

        assertThat(userProductList)
                .isNotNull()
                .hasOnlyElementsOfType(UserProduct.class);

        assertThat(userProductList.stream().mapToLong(p -> p.getProduct().getId()).count())
                .isEqualTo(userProductList.size());
    }

    @DisplayName("테스트04_정상_투자상품_신규등록")
    @Test
    void 테스트04_정상_투자상품_신규등록() {
        long newId = productService.findAll().stream().mapToLong(Product::getId).max().orElse(0) + 1;
        Product product = new Product(newId, "Test17", 54000L, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3));
        productService.add(product);

        Product searchResult = productService.findById(product.getId());
        assertThat(searchResult)
                .isNotNull()
                .isEqualTo(product);

        assertThat(productService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .contains(product);

    }

    @DisplayName("테스트05_정상_투자상품_수정")
    @Test
    void 테스트05_정상_투자상품_수정() {
        Product updatedInvestProduct = findAny();
        assertThat(updatedInvestProduct).isNotNull();

        updatedInvestProduct.setTitle("Changed Title");
        updatedInvestProduct.setStartedAt(updatedInvestProduct.getStartedAt().minusHours(2));
        updatedInvestProduct.setFinishedAt(updatedInvestProduct.getFinishedAt().plusDays(1));

        productService.modify(updatedInvestProduct);

        Product searchResult = productService.findById(updatedInvestProduct.getId());
        assertThat(searchResult)
                .isNotNull()
                .isEqualTo(updatedInvestProduct);

        assertThat(productService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .contains(updatedInvestProduct);
    }

    @DisplayName("테스트06_정상_투자상품_삭제")
    @Test
    void 테스트06_정상_투자상품_삭제() {
        Product product = new Product(null, "TestProduct", 10L);
        productService.add(product);

        productService.remove(product.getId());

        Product searchResult = productService.findById(product.getId());
        assertThat(searchResult)
                .isNull();

        assertThat(productService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .doesNotContain(product);
    }

}
