package com.kakaopay.invest.demo.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakaopay.invest.demo.model.UserProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProductDto {
    private Long id;
    private String title;
    private Long totalProductInvestAmount;
    private Long totalUserInvestAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;

    public UserProductDto(UserProduct userProduct) {
        BeanUtils.copyProperties(userProduct, this);
        id = userProduct.getProduct().getId();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProductDto{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", totalProductInvestAmount=").append(totalProductInvestAmount);
        sb.append(", totalUserInvestAmount=").append(totalUserInvestAmount);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append('}');
        return sb.toString();
    }
}
