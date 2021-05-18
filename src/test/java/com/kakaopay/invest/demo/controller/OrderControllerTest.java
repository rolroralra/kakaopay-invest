package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.model.Order;
import com.kakaopay.invest.demo.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@DisplayName("[Controller]투자상품_주문_관련_API_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public OrderControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("테스트01_정상_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1:1:100", "2:2:200", "3:6:5000000"}, delimiterString = ":")
    void 테스트01_정상_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkSuccessResult(result);
    }

    @DisplayName("테스트02_예외_투자상품_잔여수량_초과_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"4:1:100000000", "5:2:10000001", "6:6:5000001"}, delimiterString = ":")
    void 테스트02_예외_투자상품_잔여수량_초과_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkFailedResult(result);
    }

    @DisplayName("테스트03_예외_종료된_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1:5:1", "2:7:1", "3:5:0"}, delimiterString = ":")
    void 테스트03_예외_종료된_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkFailedResult(result);
    }

    @DisplayName("테스트04_매진된_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"5:8:1"}, delimiterString = ":")
    void 테스트04_매진된_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkFailedResult(result);
    }

    @DisplayName("테스트05_예외_잘못된_사용자_ID_입력한_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"999:4:10"}, delimiterString = ":")
    void 테스트05_예외_잘못된_사용자_ID_입력한_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkFailedResult(result);
    }

    @DisplayName("테스트06_예외_잘못된_투자상품_ID_입력한_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"1:999:10"}, delimiterString = ":")
    void 테스트06_예외_잘못된_투자상품_ID_입력한_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        checkFailedResult(result);
    }

    private void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.user", notNullValue()))
                .andExpect(jsonPath("$.data.user.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.items").isArray())

                .andExpect(jsonPath("$.data.items[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].product", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].product.title").isString())
                .andExpect(jsonPath("$.data.items[0].product.totalInvestingAmount").isNumber())
                .andExpect(jsonPath("$.data.items[0].product.currentAmount").isNumber())
                .andExpect(jsonPath("$.data.items[0].product.startedAt").isString())
                .andExpect(jsonPath("$.data.items[0].product.finishedAt").isString())
                .andExpect(jsonPath("$.data.items[0].product.state").isString())

                .andExpect(jsonPath("$.data.items[0].orderId", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].amount", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].state", is(OrderItem.State.PROCEED.name())))

                .andExpect(jsonPath("$.data.items.length()", greaterThan(0)))
                .andExpect(jsonPath("$.data.startedAt", notNullValue()))
                .andExpect(jsonPath("$.data.finishedAt", nullValue()))
                .andExpect(jsonPath("$.data.state", is(Order.State.PROCEED.name())));
    }

    private void checkFailedResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", nullValue()))
                .andExpect(jsonPath("$.error", notNullValue()))
                .andExpect(jsonPath("$.error").isString());
    }

}