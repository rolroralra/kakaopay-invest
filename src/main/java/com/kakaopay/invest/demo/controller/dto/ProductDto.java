package com.kakaopay.invest.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;
    private Product.State state;

    protected  ProductDto() { }

    public ProductDto(Product product) {
        BeanUtils.copyProperties(product, this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDto{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", totalInvestingAmount=").append(totalInvestingAmount);
        sb.append(", currentAmount=").append(currentAmount);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
