package com.kakaopay.invest.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Model]ApiResult_DTO_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public class ApiResultTest {
    @Getter
    @Setter
    static class CustomClass {
        private String a;
        private Integer b;

        public CustomClass(String a, Integer b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }

    @DisplayName("테스트01_정상_API_성공_결과_생성")
    @Test
    public void 테스트01_정상_API_성공_결과_생성() {
        ApiResult<?> apiResult = ApiResult.succeed(new CustomClass("test", 123));

        assertThat(isJsonValid(apiResult.toString())).isTrue();
    }

    @DisplayName("테스트02_예외_API_실패_결과_생성")
    @Test
    public void 테스트02_예외_API_실패_결과_생성() {
        ApiResult<?> apiResult = ApiResult.failed("This is error message.");

        assertThat(isJsonValid(apiResult.toString())).isTrue();
    }

    @DisplayName("테스트03_정상_예외로_인한_API_실패_결과_생성")
    @Test
    public void 테스트03_정상_예외로_인한_API_실패_결과_생성() {
        ApiResult<?> apiResult = ApiResult.failed(new IllegalArgumentException("This is Exception Message."));

        assertThat(isJsonValid(apiResult.toString())).isTrue();
    }

    private boolean isJsonValid(String jsonString) {
        try {
            new JSONObject(jsonString);
        } catch (JSONException e) {
            try {
                new JSONArray(jsonString);
            } catch (JSONException jsonException) {
                return false;
            }
        }

        return true;
    }

    public static void checkFailedResult(ResultActions result) throws Exception {
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", nullValue()))
                .andExpect(jsonPath("$.error", notNullValue()))
                .andExpect(jsonPath("$.error").isString());
    }
}
