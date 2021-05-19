package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.UserProduct;
import com.kakaopay.invest.demo.repository.OrderItemRepository;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public List<Product> findByState(Product.State state) {
        if (!Product.State.COMPLETED.equals(state)) {
            productRepository.findByState(state).stream().filter(Product::refreshState).forEach(this::modify);
        }

        return productRepository.findByState(state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserProduct> findUserProductsByUserId(Long userId) {
        Map<Long, UserProduct> map = new HashMap<>();

        List<OrderItem> orderItemList = orderItemRepository.findOrderItemsByUserId(userId);
        for (OrderItem orderItem : orderItemList) {
            Long productId = orderItem.getProduct().getId();

            if (!map.containsKey(productId)) {
                map.put(productId, new UserProduct(orderItem));
                continue;
            }

            map.get(productId).addUserAmount(orderItem.getAmount());
            map.get(productId).updateDate(orderItem.getOrder().getFinishedAt());
        }

        return new ArrayList<>(map.values());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProceedingInvestProducts() {
        return findByState(Product.State.PROCEED);
    }

    @Transactional
    @Override
    public void add(Product product) {
        if (Objects.isNull(product.getId()) || productRepository.findById(product.getId()).isEmpty()) {
            productRepository.save(product);
        }
    }

    @Transactional
    @Override
    public void modify(Product product) {
        if (productRepository.findById(product.getId()).isPresent()) {
            productRepository.save(product);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
}
