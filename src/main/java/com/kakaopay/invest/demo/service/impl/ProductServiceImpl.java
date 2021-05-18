package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findByState(Product.State state) {
        return productRepository.findByState(state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProceedingInvestProducts() {
        return productRepository.findProceedingInvestmentProducts();
    }

    @Transactional
    @Override
    public void addInvestmentProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void updateInvestmentProduct(Product product) {
        productRepository.save(product);

    }

    @Transactional
    @Override
    public void deleteInvestmentProduct(Long id) {
        productRepository.deleteById(id);
    }
}
