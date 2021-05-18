package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByState(Product.State state);

    default List<Product> findProceedingInvestmentProducts() {
        return findByState(Product.State.PROCEEDING);
    }

//    default void add(Product product) {
//        save(product);
//    }
//
//    default void modify(Product product) {
//        save(product);
//    }
//
//    default void remove(Long id) {
//        deleteById(id);
//    }
}
