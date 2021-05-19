package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ProductDtoChecker;
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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @DisplayName("테스트02_정상_모집중인_투자상품_목록_조회")
    @Test
    public void 테스트02_정상_모집중인_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/proceed")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].state", is(Product.State.PROCEED.name())));
    }

    @DisplayName("테스트03_정상_매진된_투자상품_목록_조회")
    @Test
    public void 테스트03_정상_매진된_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/soldout")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)));
    }

    @DisplayName("테스트04_정상_투자기간이_만료된_투자상품_목록_조회")
    @Test
    public void 테스트04_정상_투자기간이_만료된_투자상품_목록_조회() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/completed")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.data[0].state", is(Product.State.COMPLETED.name())));
    }

    // TODO:
    @DisplayName("테스트05_정상_특정_사용자_투자상품_전체_목록_조회")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    public void 테스트05_정상_특정_사용자_투자상품_전체_목록_조회(Long userId) throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/product/user")
                        .header("X-USER-ID", userId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(0)))
//                .andExpect(jsonPath("$.data[0].state", is(Product.State.COMPLETED.name())));
    }
}