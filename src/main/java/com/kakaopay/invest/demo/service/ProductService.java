package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    List<Product> findByState(Product.State state);

    List<Product> findProceedingInvestProducts();

    void addInvestmentProduct(Product product);

    void updateInvestmentProduct(Product product);

    void deleteInvestmentProduct(Long id);
}
