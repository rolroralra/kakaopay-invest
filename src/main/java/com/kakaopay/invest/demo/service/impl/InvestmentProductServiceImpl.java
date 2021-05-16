package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.InvestmentProduct;
import com.kakaopay.invest.demo.repository.InvestmentProductRepository;
import com.kakaopay.invest.demo.service.InvestmentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentProductServiceImpl implements InvestmentProductService {
    private final InvestmentProductRepository investmentProductRepository;

    @Autowired
    public InvestmentProductServiceImpl(InvestmentProductRepository investmentProductRepository) {
        this.investmentProductRepository = investmentProductRepository;
    }

    @Override
    public List<InvestmentProduct> findAll() {
        return investmentProductRepository.findAll();
    }

    @Override
    public InvestmentProduct findById(Long id) {
        return investmentProductRepository.findById(id).orElse(null);
    }

    @Override
    public List<InvestmentProduct> findByState(InvestmentProduct.State state) {
        return investmentProductRepository.findByState(state);
    }

    @Override
    public List<InvestmentProduct> findProceedingInvestProducts() {
        return investmentProductRepository.findProceedingInvestmentProducts();
    }

    @Override
    public void addInvestmentProduct(InvestmentProduct investmentProduct) {
        investmentProductRepository.insertInvestmentProduct(investmentProduct);
    }

    @Override
    public void updateInvestmentProduct(InvestmentProduct investmentProduct) {
        investmentProductRepository.updateInvestmentProduct(investmentProduct);

    }

    @Override
    public void deleteInvestmentProduct(Long id) {
        investmentProductRepository.deleteInvestmentProduct(id);
    }
}
