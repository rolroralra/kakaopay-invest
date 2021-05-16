package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.InvestmentProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvestmentProductServiceTest {
    private InvestmentProductService investmentProductService;
    private List<InvestmentProduct> investmentProductList;

    @Autowired
    public void setInvestmentProductService(InvestmentProductService investmentProductService) {
        this.investmentProductService = investmentProductService;
    }

    @DisplayName("투자모집상태에_따른_투자상품_목록_조회_테스트")
    @Test
    void findByState() {
        investmentProductList = investmentProductService.findByState(InvestmentProduct.State.PROCEEDING);

        assertThat(investmentProductList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class);
    }

    @DisplayName("모집중인_투자상품_목록_조회_테스트")
    @Test
    void findProceedingInvestProducts() {
        investmentProductList = investmentProductService.findProceedingInvestProducts();

        assertThat(investmentProductList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .allMatch(InvestmentProduct::isProceeding);
    }

    @DisplayName("투자상품_신규등록_테스트")
    @Test
    void addInvestmentProduct() {
        InvestmentProduct investmentProduct = new InvestmentProduct(17L, "Test17", 54000L, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(3));
        investmentProductService.addInvestmentProduct(investmentProduct);

        InvestmentProduct searchResult = investmentProductService.findById(investmentProduct.getId());
        assertThat(searchResult)
                .isNotNull()
                .isEqualTo(investmentProduct);

        assertThat(investmentProductService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .contains(investmentProduct);

    }

    @DisplayName("투자상품_수정_테스트")
    @Test
    void updateInvestmentProduct() {
        InvestmentProduct updatedInvestProduct = findAny();
        assertThat(updatedInvestProduct).isNotNull();

        updatedInvestProduct.setTitle("Changed Title");
        updatedInvestProduct.setStartedAt(updatedInvestProduct.getStartedAt().minusHours(2));
        updatedInvestProduct.setFinishedAt(updatedInvestProduct.getFinishedAt().plusDays(1));

        investmentProductService.updateInvestmentProduct(updatedInvestProduct);

        InvestmentProduct searchResult = investmentProductService.findById(updatedInvestProduct.getId());
        assertThat(searchResult)
                .isNotNull()
                .isEqualTo(updatedInvestProduct);

        assertThat(investmentProductService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .contains(updatedInvestProduct);
    }

    @DisplayName("투자상품_삭제_테스트")
    @Test
    void deleteInvestmentProduct() {
        InvestmentProduct deletedInvestmentProduct = findAny();
        assertThat(deletedInvestmentProduct).isNotNull();

        investmentProductService.deleteInvestmentProduct(deletedInvestmentProduct.getId());

        InvestmentProduct searchResult = investmentProductService.findById(deletedInvestmentProduct.getId());
        assertThat(searchResult).isNull();

        assertThat(investmentProductService.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .doesNotContain(deletedInvestmentProduct);
    }

    private InvestmentProduct findAny() {
        return investmentProductService.findAll().stream().findAny().orElse(null);
    }
}
