package com.kakaopay.invest.demo.controller.dto;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDtoChecker {
    public static void checkSuccessResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.id", greaterThan(0)))

                .andExpect(jsonPath("$.data.name", notNullValue()))
                .andExpect(jsonPath("$.data.name").isString())

                .andExpect(jsonPath("$.data.email", notNullValue()))
                .andExpect(jsonPath("$.data.email").isString());
    }

    public static void checkSuccessResultForList(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.error", nullValue()))

                .andExpect(jsonPath("$.data[0].id", notNullValue()))
                .andExpect(jsonPath("$.data[0].id").isNumber())
                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))

                .andExpect(jsonPath("$.data[0].name", notNullValue()))
                .andExpect(jsonPath("$.data[0].name").isString())

                .andExpect(jsonPath("$.data[0].email", notNullValue()))
                .andExpect(jsonPath("$.data[0].email").isString());
    }


}
