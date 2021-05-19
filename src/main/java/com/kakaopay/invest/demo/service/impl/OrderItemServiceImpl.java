package com.kakaopay.invest.demo.service.impl;

import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.repository.OrderItemRepository;
import com.kakaopay.invest.demo.repository.ProductRepository;
import com.kakaopay.invest.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public OrderItem createOrderItem(Long productId, Long amount) {
        Product product = productRepository.findById(productId).orElse(null);

        checkArgument(Objects.nonNull(product), "Product not exists. [ID=%s]", productId);
        checkArgument(product.isSellable(amount), "Product is not sellable [total: %s, current: %s]", product.getTotalInvestingAmount(), product.getCurrentAmount());

        OrderItem orderItem = new OrderItem(product, amount);
        orderItemRepository.save(orderItem);

        return orderItem;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderItem> findOrderItemsByUserId(Long userId) {
        return orderItemRepository.findByUser(userId);
    }

}
