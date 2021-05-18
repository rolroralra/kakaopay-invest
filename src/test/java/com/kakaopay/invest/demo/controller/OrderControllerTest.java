package com.kakaopay.invest.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller]투자상품_주문_관련_API_테스트")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public OrderControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("투자상품_주문_테스트")
    @Test
    void takeOrder() throws Exception{
        ResultActions result = mockMvc.perform(
                post("/api/order")
                        .header("X-USER-ID", 1L)
                        .param("productId", "1")
                        .param("amount", "100")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk());
    }
}