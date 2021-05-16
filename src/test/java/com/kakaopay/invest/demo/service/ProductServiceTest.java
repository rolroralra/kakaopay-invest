package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Service]투자상품_서비스_테스트")
@SpringBootTest
public class ProductServiceTest {
    private ProductService productService;
    private List<Product> productList;

    @Autowired
    public void setInvestmentProductService(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("투자모집상태에_따른_투자상품_목록_조회_테스트")
    @Test
    void findByState() {
        productList = productService.findByState(Product.State.PROCEEDING);

        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class);
    }

    @DisplayName("모집중인_투자상품_목록_조회_테스트")
    @Test
    void findProceedingInvestProducts() {
        productList = productService.findProceedingInvestProducts();

        assertThat(productList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .allMatch(Product::isProceeding);
    }

    @DisplayName("투자상품_신규등록_테스트")
    @Test
    void addInvestmentProduct() {
        Product product = new Product(17L, "Test17", 54000L, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3));
        productService.addInvestmentProduct(product);

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

    @DisplayName("투자상품_수정_테스트")
    @Test
    void updateInvestmentProduct() {
        Product updatedInvestProduct = findAny();
        assertThat(updatedInvestProduct).isNotNull();

        updatedInvestProduct.setTitle("Changed Title");
        updatedInvestProduct.setStartedAt(updatedInvestProduct.getStartedAt().minusHours(2));
        updatedInvestProduct.setFinishedAt(updatedInvestProduct.getFinishedAt().plusDays(1));

        productService.updateInvestmentProduct(updatedInvestProduct);

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

    @DisplayName("투자상품_삭제_테스트")
    @Test
    void deleteInvestmentProduct() {
        Product deletedProduct = findAny();
        assertThat(deletedProduct).isNotNull();

        productService.deleteInvestmentProduct(deletedProduct.getId());

        Product searchResult = productService.findById(deletedProduct.getId());
        assertThat(searchResult).isNull();

        assertThat(productService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(Product.class)
                .doesNotContain(deletedProduct);
    }

    private Product findAny() {
        return productService.findAll().stream().findAny().orElse(null);
    }
}
