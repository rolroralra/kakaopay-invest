package com.kakaopay.invest.demo.repository.impl;

import com.kakaopay.invest.demo.model.InvestmentProduct;
import com.kakaopay.invest.demo.repository.InvestmentProductRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
public class InvestmentProductRepositoryImpl implements InvestmentProductRepository {
    public Map<Long, InvestmentProduct> map;

    public InvestmentProductRepositoryImpl() {
        map = new HashMap<>();

        LongStream.range(1, 11).forEach(l -> map.put(l, new InvestmentProduct(l, "product" + l, l * 1000, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(l))));

        map.get(1L).setState(InvestmentProduct.State.COMPLETED);
        map.get(1L).setFinishedAt(LocalDateTime.now().minusDays(1));

        map.get(4L).setState(InvestmentProduct.State.COMPLETED);
        map.get(4L).setFinishedAt(LocalDateTime.now().minusDays(1));

        map.get(7L).setState(InvestmentProduct.State.COMPLETED);
        map.get(7L).setFinishedAt(LocalDateTime.now().minusDays(1));
    }

    @Override
    public List<InvestmentProduct> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<InvestmentProduct> findById(Long id) {
        if (map.containsKey(id)) {
            return Optional.of(map.get(id));
        }

        return Optional.empty();
    }

    @Override
    public List<InvestmentProduct> findByState(InvestmentProduct.State state) {
        return map.values().stream().filter(prod -> prod.getState() == state).collect(Collectors.toList());
    }

    @Override
    public List<InvestmentProduct> findProceedingInvestmentProducts() {
        return map.values().stream().filter(InvestmentProduct::isProceeding).collect(Collectors.toList());
    }

    @Override
    public void insertInvestmentProduct(InvestmentProduct investmentProduct) {
        map.put(investmentProduct.getId(), investmentProduct);
    }

    @Override
    public void updateInvestmentProduct(InvestmentProduct investmentProduct) {
        map.put(investmentProduct.getId(), investmentProduct);
    }

    @Override
    public void deleteInvestmentProduct(Long id) {
        map.remove(id);
//        map.computeIfPresent(id, (key, oldValue) -> null);
    }
}
