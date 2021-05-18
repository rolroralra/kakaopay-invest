package com.kakaopay.invest.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("성공_결과_생성_테스트")
    @Test
    public void 성공_결과_생성_테스트() {
        ApiResult<?> apiResult = ApiResult.succeed(new CustomClass("test", 123));

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
}
