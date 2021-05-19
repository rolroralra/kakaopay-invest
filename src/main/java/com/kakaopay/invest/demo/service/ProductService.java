package com.kakaopay.invest.demo.service;

import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.UserProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    List<Product> findByState(Product.State state);

    List<Product> findProceedingInvestProducts();

    List<UserProduct> findUserProductsByUserId(Long userId);

    void add(Product product);

    void modify(Product product);

    void remove(Long id);
}
