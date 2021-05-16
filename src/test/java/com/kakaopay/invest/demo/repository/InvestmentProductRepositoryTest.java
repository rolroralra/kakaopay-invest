package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.InvestmentProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("투자상품_레퍼지토리_테스트")
@SpringBootTest
public class InvestmentProductRepositoryTest {
    private InvestmentProductRepository investmentProductRepository;
    private List<InvestmentProduct> investmentProductList;

    @Autowired
    public void setInvestmentProductRepository(InvestmentProductRepository investmentProductRepository) {
        this.investmentProductRepository = investmentProductRepository;
    }

    @DisplayName("투자상품_전체_목록_조회_테스트")
    @Test
    void findAll() {
        investmentProductList = investmentProductRepository.findAll();
        assertThat(investmentProductList)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class);
    }

    @DisplayName("모집상태를_통한_투자상품_목록_조회_테스트")
    @Test
    void findByState() {
        investmentProductList = investmentProductRepository.findByState(InvestmentProduct.State.PROCEEDING);
        assertThat(investmentProductList)
                .isNotNull()
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .allMatch(InvestmentProduct::isProceeding);
    }

    @DisplayName("모집중인_투자상품_목록_조회_테스트")
    @Test
    void findProceedingInvestmentProducts() {
        investmentProductList = investmentProductRepository.findProceedingInvestmentProducts();
        assertThat(investmentProductList)
                .isNotNull()
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .allMatch(InvestmentProduct::isProceeding);
    }

    @DisplayName("신규_투자상품_INSERT_테스트")
    @Test
    void insertInvestmentProduct() {
        InvestmentProduct investmentProduct = new InvestmentProduct(17L, "Test-17", 24000L, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(3));
        investmentProductRepository.insertInvestmentProduct(investmentProduct);
        investmentProductList = investmentProductRepository.findAll();

        assertThat(investmentProductList)
                .isNotNull()
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .contains(investmentProduct);
    }

    @DisplayName("기존_투자상품_UPDATE_테스트")
    @Test
    void updateInvestmentProduct() {
        InvestmentProduct updatedInvestmentProduct = getAnyInvestmentProduct();

        updatedInvestmentProduct.setTitle("Changed Title Test");
        updatedInvestmentProduct.setStartedAt(updatedInvestmentProduct.getStartedAt().minusDays(2));
        updatedInvestmentProduct.setFinishedAt(updatedInvestmentProduct.getFinishedAt().plusDays(1));
        investmentProductRepository.updateInvestmentProduct(updatedInvestmentProduct);

        Optional<InvestmentProduct> searchResult = investmentProductRepository.findById(updatedInvestmentProduct.getId());

        assertThat(searchResult)
                .satisfies(Optional::isPresent)
                .hasValue(updatedInvestmentProduct);

        assertThat(investmentProductRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .contains(updatedInvestmentProduct);
    }

    @DisplayName("기존_투자상품_DELETE_테스트")
    @Test
    void deleteInvestmentProduct() {
        InvestmentProduct deletedInvestmentProduct = getAnyInvestmentProduct();

        investmentProductRepository.deleteInvestmentProduct(deletedInvestmentProduct.getId());

        Optional<InvestmentProduct> searchResult = investmentProductRepository.findById(deletedInvestmentProduct.getId());
        assertThat(searchResult.isEmpty()).isTrue();

        assertThat(investmentProductRepository.findAll())
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(0)
                .hasOnlyElementsOfType(InvestmentProduct.class)
                .doesNotContain(deletedInvestmentProduct);
    }

    private InvestmentProduct getAnyInvestmentProduct() {
        InvestmentProduct investmentProduct = investmentProductRepository.findAll().stream().findAny().orElse(null);
        assertThat(investmentProduct).isNotNull();
        return investmentProduct;
    }
}
