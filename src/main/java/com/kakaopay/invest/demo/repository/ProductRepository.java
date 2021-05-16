package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByState(Product.State state);

    List<Product> findProceedingInvestmentProducts();

    void add(Product product);

    void modify(Product product);

    void remove(Long id);
}
