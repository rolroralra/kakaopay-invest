package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResultTest;
import com.kakaopay.invest.demo.controller.dto.ProductDtoChecker;
import com.kakaopay.invest.demo.controller.dto.UserProductDtoChecker;
import com.kakaopay.invest.demo.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller]투자상품_관련_API_테스트")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    ProductControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("테스트01_정상_전체_투자상품_목록_조회")
    @ParameterizedTest
    @CsvSource(value = {"0:5:id,ASC"}, delimiterString = ":")
    public void 테스트01_정상_전체_투자상품_목록_조회(String page, String size, String sort) throws Exception{
        ResultActions result = mockMvc.perform(
                get("/api/product")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sort", sort)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResultForList(result);
    }

    @DisplayName("테스트02_정상_특정_ID_투자상품_조회")
    @ParameterizedTest
    @ValueSource(longs = {1,2,3,4,5,6,})
    public void 테스트02_정상_특정_ID_투자상품_조회(Long productId) throws Exception{
        ResultActions result = mockMvc.perform(
                get("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResult(result);
    }

    @DisplayName("테스트03_정상_모집중인_투자상품_목록_조회")
    @Test
    public void 테스트03_정상_모집중인_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/proceed")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResultForList(result);

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].state", is(Product.State.PROCEED.name())));
    }

    @DisplayName("테스트04_정상_매진된_투자상품_목록_조회")
    @Test
    public void 테스트04_정상_매진된_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/soldout")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResultForList(result);

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)));
    }

    @DisplayName("테스트05_정상_투자기간이_만료된_투자상품_목록_조회")
    @Test
    public void 테스트05_정상_투자기간이_만료된_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/completed")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResultForList(result);

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].state", is(Product.State.COMPLETED.name())));
    }

    @DisplayName("테스트06_정상_특정_사용자_투자상품_전체_목록_조회")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    public void 테스트06_정상_특정_사용자_투자상품_전체_목록_조회(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/user")
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        UserProductDtoChecker.checkSuccessResultForList(result);
    }

    @DisplayName("테스트07_예외_존재하지_않는_사용자_투자상품_전체_목록_조회")
    @ParameterizedTest
    @ValueSource(longs = {-100, -1, 999})
    public void 테스트07_예외_존재하지_않는_사용자_투자상품_전체_목록_조회(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/user")
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트08_정상_투자상품_추가")
    @ParameterizedTest
    @CsvSource(value = {
            "TSMC|50000|2021-05-20 09:00:00|2021-07-01 09:00:00",
            "STARBUCKS|20000|2021-05-19 09:00:00|2021-07-01 09:00:00"
    }, delimiterString = "|")
    public void 테스트08_정상_투자상품_추가(String title, Long totalInvestingAmount, String startedAt, String finishedAt) throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"title\": \""+ title + "\",\n" +
                                "    \"totalInvestingAmount\": " + totalInvestingAmount + ",\n" +
                                "    \"startedAt\": \"" + startedAt + "\",\n" +
                                "    \"finishedAt\": \"" + finishedAt + "\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResult(result);
    }

    @DisplayName("테스트09_정상_투자상품_수정")
    @ParameterizedTest
    @CsvSource(value = {
            "1|NAVER|250000|2021-05-14 09:00:00|2021-05-18 09:00:00",
            "2|KAKAO|250000|2021-05-14 09:00:00|2021-05-18 09:00:00",
            "3|APPLE|500000|2021-05-14 09:00:00|2021-05-18 09:00:00",
            "4|SKIET|150000|2021-05-19 09:00:00|2021-07-01 09:00:00",
            "5|SAMSUNG|500000|2021-05-19 09:00:00|2021-07-01 09:00:00",
            "6|IBM|300000|2021-05-20 19:00:00|2021-07-01 09:00:00",
            "7|GOOGLE|600000|2021-05-19 09:00:00|2021-07-01 09:00:00",
            "8|TESLA|550000|2021-05-19 09:00:00|2021-07-01 09:00:00",
            "9|TSMC|600000|2021-05-19 09:00:00|2021-07-01 09:00:00",
    }, delimiterString = "|")
    public void 테스트09_정상_투자상품_수정(Long productId, String title, Long totalInvestingAmount, String startedAt, String finishedAt) throws Exception {
        ResultActions result = mockMvc.perform(
                put("/api/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"title\": \""+ title + "\",\n" +
                                "    \"totalInvestingAmount\": " + totalInvestingAmount + ",\n" +
                                "    \"startedAt\": \"" + startedAt + "\",\n" +
                                "    \"finishedAt\": \"" + finishedAt + "\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResult(result);
    }

    @DisplayName("테스트10_예외_존재하지_않는_투자상품_수정")
    @ParameterizedTest
    @CsvSource(value = {"-10|TSMC|20000|2021-05-20 09:00:00|2021-07-01 09:00:00"}, delimiterString = "|")
    public void 테스트10_예외_존재하지_않는_투자상품_수정(Long productId, String title, Long totalInvestingAmount, String startedAt, String finishedAt) throws Exception {
        ResultActions result = mockMvc.perform(
                put("/api/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"title\": \""+ title + "\",\n" +
                                "    \"totalInvestingAmount\": " + totalInvestingAmount + ",\n" +
                                "    \"startedAt\": \"" + startedAt + "\",\n" +
                                "    \"finishedAt\": \"" + finishedAt + "\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트11_정상_투자상품_삭제")
    @ParameterizedTest
    @ValueSource(longs = {8,9})
    public void 테스트11_정상_투자상품_삭제(Long productId) throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResult(result);

        result = mockMvc.perform(
                get("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트12_예외_존재하지_않는_투자상품_삭제")
    @ParameterizedTest
    @ValueSource(longs = {-1, 100, -100})
    public void 테스트12_예외_존재하지_않는_투자상품_삭제(Long productId) throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);

        result = mockMvc.perform(
                get("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);
    }

    @DisplayName("테스트13_예외_이미_투자_주문이_있는_투자상품_삭제")
    @ParameterizedTest
    @ValueSource(longs = {1, 3, 5})
    public void 테스트13_예외_이미_투자_주문이_있는_투자상품_삭제(Long productId) throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ApiResultTest.checkFailedResult(result);

        result = mockMvc.perform(
                get("/api/product/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        ProductDtoChecker.checkSuccessResult(result);
    }
}