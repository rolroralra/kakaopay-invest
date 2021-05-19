package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // TODO:
    List<Product> findByFinishedAtAfter(LocalDateTime now);
    List<Product> findByState(Product.State state);
}
