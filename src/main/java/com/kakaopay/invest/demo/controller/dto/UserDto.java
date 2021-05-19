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

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
