package com.kakaopay.invest.demo.controller.dto;

import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderItemDtoChecker {
    public static void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].amount", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].startedAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].finishedAt", notNullValue()))

                .andExpect(jsonPath("$.data[0].product", notNullValue()))
                .andExpect(jsonPath("$.data[0].product.id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].product.title", notNullValue()))
                .andExpect(jsonPath("$.data[0].product.totalInvestingAmount", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].product.currentAmount", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].product.startedAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].product.finishedAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].product.state", notNullValue()));
    }
}
