package com.kakaopay.invest.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakaopay.invest.demo.controller.dto.ApiResultTest;
import com.kakaopay.invest.demo.controller.dto.OrderDtoChecker;
import com.kakaopay.invest.demo.controller.dto.OrderItemDtoChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    @CsvSource(value = {"1:1:100", "2:2:200", "3:6:100"}, delimiterString = ":")
    void 테스트01_정상_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .param("productId", productId)
                        .param("amount", amount)
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        OrderDtoChecker.checkSuccessResult(result);
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

        ApiResultTest.checkFailedResult(result);
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

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트04_매진된_투자상품_주문_테스트")
    @ParameterizedTest
    @CsvSource(value = {"5:9:5"}, delimiterString = ":")
    void 테스트04_매진된_투자상품_주문_테스트(String userId, String productId, String amount) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", userId)
                        .param("productId", productId)
                        .param("amount", amount)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
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

        ApiResultTest.checkFailedResult(result);
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

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트07_정상_사용자_투자상품_조회_테스트")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6"})
    void 테스트07_정상_사용자_투자상품_조회_테스트(String userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/order/user")
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        OrderItemDtoChecker.checkSuccessResult(result);
//        result.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data", notNullValue()))
//                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("테스트08_예외_존재하지_않는_사용자_투자상품_조회_테스트")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, 1000, 9999, 20})
    void 테스트07_정상_사용자_투자상품_조회_테스트(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/order/user")
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }


    private String toJsonString(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}