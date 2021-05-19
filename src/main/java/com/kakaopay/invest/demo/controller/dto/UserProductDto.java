package com.kakaopay.invest.demo.controller.dto;

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
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    public UserProductDto(UserProduct userProduct) {
        BeanUtils.copyProperties(userProduct, this);
    }
}
