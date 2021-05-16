package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.InvestmentProduct;

import java.util.List;

public interface InvestmentProductService {
    List<InvestmentProduct> findAll();

    InvestmentProduct findById(Long id);

    List<InvestmentProduct> findByState(InvestmentProduct.State state);

    List<InvestmentProduct> findProceedingInvestProducts();

    void addInvestmentProduct(InvestmentProduct investmentProduct);

    void updateInvestmentProduct(InvestmentProduct investmentProduct);

    void deleteInvestmentProduct(Long id);
}
