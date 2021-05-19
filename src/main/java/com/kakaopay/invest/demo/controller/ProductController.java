package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.controller.dto.ProductDto;
import com.kakaopay.invest.demo.controller.dto.UserDto;
import com.kakaopay.invest.demo.controller.dto.UserProductDto;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.model.UserProduct;
import com.kakaopay.invest.demo.service.ProductService;
import com.kakaopay.invest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kakaopay.invest.demo.controller.dto.ApiResult.failed;
import static com.kakaopay.invest.demo.controller.dto.ApiResult.succeed;


@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ApiResult<List<ProductDto>> getAllProducts(Pageable pageable) {
        return ApiResult.succeed(
                productService.findAll(pageable).get().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/proceed", method = { RequestMethod.GET })
    ApiResult<List<ProductDto>> getProceedingInvestmentProducts() {
        return ApiResult.succeed(
                productService.findByState(Product.State.PROCEED).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/soldout", method = { RequestMethod.GET })
    ApiResult<List<ProductDto>> getSoldOutProducts() {
        return ApiResult.succeed(
                productService.findByState(Product.State.SOLD_OUT).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/completed", method = { RequestMethod.GET })
    ApiResult<List<ProductDto>> getCompletedProducts() {
        return ApiResult.succeed(
                productService.findByState(Product.State.COMPLETED).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/user", method = { RequestMethod.GET })
    public ApiResult<List<UserProductDto>> findUserProductList(@RequestHeader(name = "X-USER-ID") Long userId) {
        User user = userService.findById(userId);

        if (Objects.isNull(user)) {
            return (ApiResult<List<UserProductDto>>) failed(String.format("User is not exists. [ID=%s]", userId));
        }

        for (UserProduct userProduct : productService.findUserProductsByUserId(userId)) {
            System.out.println(userProduct);
        }

        return ApiResult.succeed(
                productService.findUserProductsByUserId(userId).stream().map(UserProductDto::new).collect(Collectors.toList())
        );
    }



}
