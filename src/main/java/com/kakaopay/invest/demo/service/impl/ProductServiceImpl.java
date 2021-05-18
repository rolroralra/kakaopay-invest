package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByState(Product.State state) {
        return productRepository.findByState(state);
    }

    @Override
    public List<Product> findProceedingInvestProducts() {
        return productRepository.findProceedingInvestmentProducts();
    }

    @Override
    public void addInvestmentProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateInvestmentProduct(Product product) {
        productRepository.save(product);

    }

    @Override
    public void deleteInvestmentProduct(Long id) {
        productRepository.deleteById(id);
    }
}
