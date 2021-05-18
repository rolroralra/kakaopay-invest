package com.kakaopay.invest.demo.controller.dto;

import com.kakaopay.invest.demo.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;

    protected UserDto() {

    }

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
