package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.InvestmentProduct;

import java.util.List;
import java.util.Optional;

public interface InvestmentProductRepository {
    List<InvestmentProduct> findAll();

    Optional<InvestmentProduct> findById(Long id);

    List<InvestmentProduct> findByState(InvestmentProduct.State state);

    List<InvestmentProduct> findProceedingInvestmentProducts();

    void insertInvestmentProduct(InvestmentProduct investmentProduct);

    void updateInvestmentProduct(InvestmentProduct investmentProduct);

    void deleteInvestmentProduct(Long id);
}
