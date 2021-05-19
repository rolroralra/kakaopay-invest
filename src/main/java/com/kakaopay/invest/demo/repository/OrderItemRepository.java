package com.kakaopay.invest.demo.repository;

import com.kakaopay.invest.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "SELECT item from OrderItem item INNER JOIN item.order o INNER JOIN item.product p WHERE o.user.id = :userId")
    List<OrderItem> findByUser(@Param("userId") Long userId);
}
