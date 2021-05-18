package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String title;
    private Long totalInvestingAmount;
    private Long currentAmount;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Product.State state;

    protected ProductDto() {
    }

    public ProductDto(Product product) {
        BeanUtils.copyProperties(product, this);
    }
}
