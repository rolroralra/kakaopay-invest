package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByState(Product.State state);

    default List<Product> findProceedingInvestmentProducts() {
        return findByState(Product.State.PROCEED);
    }
}
