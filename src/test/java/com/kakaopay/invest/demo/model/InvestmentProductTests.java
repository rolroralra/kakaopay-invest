package com.kakaopay.invest.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class InvestmentProductTests {

    @DisplayName("InvestmentProduct_정상_생성_테스트")
    @Test
    public void InvestmentProduct_정상_생성_테스트() {
        InvestmentProduct investmentProduct = new InvestmentProduct(1L, "개인신용 포트폴리오", 1000000L, "2021-03-01 00:00:00", "2021-03-08 12:30:10");
        assertThat(investmentProduct).isNotNull();
    }

    @DisplayName("InvestmentProduct_생성시_ID_음수값_예외_테스트")
    @ValueSource(longs = {-1000, 0, -1})

    @ParameterizedTest
    public void InvestmentProduct_생성시_ID_음수값_예외_테스트(Long id) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new InvestmentProduct(id, "개인신용 포트폴리오", 1000000L, LocalDateTime.now(), LocalDateTime.now().plusDays(7))
                ).withMessageMatching("ID should be positive \\[[-]?\\d*]");
    }

    @DisplayName("InvestmentProduct_생성시_총_투자_모집금액_음수값_예외_테스트")
    @ValueSource(longs = {-1000, 0, -1})
    @ParameterizedTest
    public void InvestmentProduct_생성시_총_투자_모집금액_음수값_예외_테스트(Long totalInvestingAmount) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new InvestmentProduct(1L, "개인신용 포트폴리오", totalInvestingAmount, LocalDateTime.now(), LocalDateTime.now().plusDays(7))
                ).withMessageMatching("Total Investing Amount should be positive \\[[-]?\\d*]");
    }

    @DisplayName("InvestmentProduct_생성시_투자시작일시_투자종료일시_역순_예외_테스트")
    @CsvSource(value = {
            "2021-03-09 00:00:00,2021-03-08 12:30:10",
    }, delimiterString = ",")
    @ParameterizedTest
    public void InvestmentProduct_생성시_투자시작일시_투자종료일시_역순_예외_테스트(String startedAt, String finishedAt) {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->
                        new InvestmentProduct(1L, "개인신용 포트폴리오", 1000000L, startedAt, finishedAt)
                ).withMessageMatching("StartedAt: " + InvestmentProduct.DATE_TIME_REGEX_FORMAT + ", FinishedAt: " + InvestmentProduct.DATE_TIME_REGEX_FORMAT);
    }

    @DisplayName("InvestmentProduct_생성시_투자시작일시_투자종료일시_DateTimeFormat_예외_테스트")
    @CsvSource(value = {
            "2021-03-01 00:61:00,2021-03-08 12:30:10",
            "2021-03-01 24:10:00,2021-03-08 12:30:10",
    }, delimiterString = ",")
    @ParameterizedTest
    public void InvestmentProduct_생성시_투자시작일시_투자종료일시_DateTimeFormat_예외_테스트(String startedAt, String finishedAt) {
        assertThatExceptionOfType(DateTimeParseException.class)
                .isThrownBy(() ->
                        new InvestmentProduct(1L, "개인신용 포트폴리오", 1000000L, startedAt, finishedAt)
                );
    }
}
