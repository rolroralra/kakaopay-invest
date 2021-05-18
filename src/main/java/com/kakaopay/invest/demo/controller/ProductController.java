package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "")
    ApiResult<List<Product>> getProceedingInvestmentProducts() {
        return ApiResult.succeed(productService.findProceedingInvestProducts());
    }

}
