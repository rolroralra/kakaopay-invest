package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResultTest;
import com.kakaopay.invest.demo.controller.dto.UserDtoChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("[Controller]사용자_관련_API_테스트")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("테스트01_정상_사용자_목록_조회")
    @ParameterizedTest
    @CsvSource(value = {"1:2:id"}, delimiterString = ":")
    void 테스트01_정상_사용자_목록_조회(String page, String size, String sort) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/user")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sort", sort)
                        .accept(MediaType.APPLICATION_JSON)
        );

        UserDtoChecker.checkSuccessResultForList(result);
    }

    @DisplayName("테스트02_정상_특정_사용자_정보_조회")
    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6})
    void 테스트02_정상_특정_사용자_정보_조회(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/user/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        UserDtoChecker.checkSuccessResult(result);
    }

    @DisplayName("테스트03_예외_존재하지_않는_사용자_정보_조회")
    @ParameterizedTest
    @ValueSource(longs = {-10, -1, 0, 10000})
    void 테스트03_예외_존재하지_않는_사용자_정보_조회(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/user/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }

}