package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.Order;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderDtoChecker {
    public static void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.startedAt", notNullValue()))
                .andExpect(jsonPath("$.data.finishedAt", notNullValue()))
                .andExpect(jsonPath("$.data.state", notNullValue()))
                .andExpect(jsonPath("$.data.state").isString())
                .andExpect(jsonPath("$.data.state", is(Order.State.COMPLETED.name())))

                .andExpect(jsonPath("$.data.user", notNullValue()))
                .andExpect(jsonPath("$.data.user.id", notNullValue()))
                .andExpect(jsonPath("$.data.user.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.user.id").isNumber())
                .andExpect(jsonPath("$.data.user.name", notNullValue()))
                .andExpect(jsonPath("$.data.user.name").isString())
                .andExpect(jsonPath("$.data.user.email", notNullValue()))
                .andExpect(jsonPath("$.data.user.email").isString())

                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.items[0].id", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].id").isNumber())
                .andExpect(jsonPath("$.data.items[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].amount", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].amount").isNumber())
                .andExpect(jsonPath("$.data.items[0].amount", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].startedAt", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].finishedAt", notNullValue()))

                .andExpect(jsonPath("$.data.items[0].product", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.id", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.id").isNumber())
                .andExpect(jsonPath("$.data.items[0].product.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].product.title", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.title").isString())
                .andExpect(jsonPath("$.data.items[0].product.totalInvestingAmount", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.totalInvestingAmount").isNumber())
                .andExpect(jsonPath("$.data.items[0].product.totalInvestingAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].product.currentAmount", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.currentAmount").isNumber())
                .andExpect(jsonPath("$.data.items[0].product.currentAmount", greaterThan(0)))
                .andExpect(jsonPath("$.data.items[0].product.startedAt", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.finishedAt", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.state", notNullValue()))
                .andExpect(jsonPath("$.data.items[0].product.state").isString());
    }
}
