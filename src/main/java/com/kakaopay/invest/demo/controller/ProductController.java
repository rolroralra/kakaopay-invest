package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.controller.dto.ProductDto;
import com.kakaopay.invest.demo.controller.dto.UserProductDto;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.model.UserProduct;
import com.kakaopay.invest.demo.service.ProductService;
import com.kakaopay.invest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
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

    @GetMapping(value = "")
    ApiResult<List<ProductDto>> getAllProducts(Pageable pageable) {
        return ApiResult.succeed(
                productService.findAll(pageable).get().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/{productId}")
    ApiResult<ProductDto> getProductById(@PathVariable Long productId) {
        Product product = productService.findById(productId);

        if (Objects.isNull(product)) {
            return (ApiResult<ProductDto>) failed(String.format("Product is not exists. [ID=%s]", productId));
        }

        return ApiResult.succeed(new ProductDto(product));
    }

    @PostMapping(value = "")
    public ApiResult<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        checkProductDtoArgument(productDto);

        Product product = new Product(productDto);
        productService.add(product);

        product = productService.findById(product.getId());

        if (Objects.isNull(product)) {
            return (ApiResult<ProductDto>) failed("Failed to add Product");
        }

        return succeed(new ProductDto(product));
    }

    @PutMapping(value = "")
    public ApiResult<ProductDto> modifyProduct(@RequestBody ProductDto productDto) {
        checkProductDtoArgument(productDto);

        Product product = productService.findById(productDto.getId());

        if (Objects.isNull(product)) {
            return (ApiResult<ProductDto>) failed(String.format("Product is not exists. [ID=%s]", productDto.getId()));
        }

        product.modify(productDto);

        productService.modify(product);

        return succeed(productDto);
    }

    @DeleteMapping(value = "/{productId}")
    public ApiResult<ProductDto> removeProduct(@PathVariable Long productId) {
        Product product = productService.findById(productId);

        if (Objects.isNull(product)) {
            return (ApiResult<ProductDto>) failed(String.format("Product is not exists. [ID=%s]", productId));
        }

        productService.remove(productId);

        return succeed(new ProductDto(product));
    }


    @GetMapping(value = "/proceed")
    ApiResult<List<ProductDto>> getProceedingInvestmentProducts() {
        return succeed(
                productService.findByState(Product.State.PROCEED).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/soldout")
    ApiResult<List<ProductDto>> getSoldOutProducts() {
        return succeed(
                productService.findByState(Product.State.SOLD_OUT).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/completed")
    ApiResult<List<ProductDto>> getCompletedProducts() {
        return succeed(
                productService.findByState(Product.State.COMPLETED).stream().map(ProductDto::new).collect(Collectors.toList())
        );
    }

    @GetMapping(value = "/user")
    public ApiResult<List<UserProductDto>> findUserProductList(@RequestHeader(name = "X-USER-ID") Long userId) {
        User user = userService.findById(userId);

        if (Objects.isNull(user)) {
            return (ApiResult<List<UserProductDto>>) failed(String.format("User is not exists. [ID=%s]", userId));
        }

        for (UserProduct userProduct : productService.findUserProductsByUserId(userId)) {
            System.out.println(userProduct);
        }

        return succeed(
                productService.findUserProductsByUserId(userId).stream().map(UserProductDto::new).collect(Collectors.toList())
        );
    }

    private void checkProductDtoArgument(ProductDto productDto) {
        checkArgument(Objects.nonNull(productDto));
        checkArgument(Objects.nonNull(productDto.getId()));

    }




}
