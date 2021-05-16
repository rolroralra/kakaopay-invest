package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.model.InvestmentProduct;
import com.kakaopay.invest.demo.service.InvestmentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/product")
public class InvestmentProductController {
    private final InvestmentProductService investmentProductService;

    @Autowired
    public InvestmentProductController(InvestmentProductService investmentProductService) {
        this.investmentProductService = investmentProductService;
    }

    @GetMapping(value = "")
    ApiResult<List<InvestmentProduct>> getProceedingInvestmentProducts() {
        return ApiResult.succeed(investmentProductService.findProceedingInvestProducts());
    }

}
