package com.kakaopay.invest.demo.controller.dto;

import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserProductDtoChecker {
    public static void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.title", notNullValue()))
                .andExpect(jsonPath("$.data.title").isString())
                .andExpect(jsonPath("$.data.totalProductInvestAmount", notNullValue()))
                .andExpect(jsonPath("$.data.totalProductInvestAmount").isNumber())
                .andExpect(jsonPath("$.data.totalProductInvestAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data.totalUserInvestAmount", notNullValue()))
                .andExpect(jsonPath("$.data.totalUserInvestAmount").isNumber())
                .andExpect(jsonPath("$.data.totalUserInvestAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data.finishedAt", notNullValue()));
    }

    public static void checkSuccessResultForList(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))

                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].title", notNullValue()))
                .andExpect(jsonPath("$.data[0].title").isString())
                .andExpect(jsonPath("$.data[0].totalProductInvestAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].totalProductInvestAmount").isNumber())
                .andExpect(jsonPath("$.data[0].totalProductInvestAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].totalUserInvestAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].totalUserInvestAmount").isNumber())
                .andExpect(jsonPath("$.data[0].totalUserInvestAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].finishedAt", notNullValue()));
    }
}
