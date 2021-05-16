package com.kakaopay.invest.demo.repository.impl;

import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
public class ProductRepositoryImpl extends AbstractRepository<Product> implements ProductRepository {
    public ProductRepositoryImpl() {
        super();

        LongStream.range(1, 11).forEach(l -> map.put(l, new Product(l, "product" + l, l * 1000, LocalDateTime.now().minusDays(3), LocalDateTime.now().plusDays(l))));

        map.get(1L).setState(Product.State.COMPLETED);
        map.get(1L).setFinishedAt(LocalDateTime.now().minusDays(1));

        map.get(4L).setState(Product.State.COMPLETED);
        map.get(4L).setFinishedAt(LocalDateTime.now().minusDays(1));

        map.get(7L).setState(Product.State.COMPLETED);
        map.get(7L).setFinishedAt(LocalDateTime.now().minusDays(1));
    }

    @Override
    public List<Product> findAll() {
        return selectAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return selectById(id);
    }

    @Override
    public List<Product> findByState(Product.State state) {
        return map.values().stream().filter(prod -> prod.getState() == state).collect(Collectors.toList());
    }

    @Override
    public List<Product> findProceedingInvestmentProducts() {
        return map.values().stream().filter(Product::isProceeding).collect(Collectors.toList());
    }

    @Override
    public void add(Product product) {
        insert(product.getId(), product);
    }

    @Override
    public void modify(Product product) {
        update(product.getId(), product);
    }

    @Override
    public void remove(Long id) {
        delete(id);
    }
}
