package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.Product;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductDtoChecker {
    public static void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].title", notNullValue()))
                .andExpect(jsonPath("$.data[0].title").isString())
                .andExpect(jsonPath("$.data[0].totalInvestingAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].totalInvestingAmount").isNumber())
                .andExpect(jsonPath("$.data[0].totalInvestingAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].currentAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].currentAmount").isNumber())
                .andExpect(jsonPath("$.data[0].currentAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].startedAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].state", notNullValue()));
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
                .andExpect(jsonPath("$.data[0].totalInvestingAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].totalInvestingAmount").isNumber())
                .andExpect(jsonPath("$.data[0].totalInvestingAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].currentAmount", notNullValue()))
                .andExpect(jsonPath("$.data[0].currentAmount").isNumber())
                .andExpect(jsonPath("$.data[0].currentAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].startedAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].state", notNullValue()));
    }
}
