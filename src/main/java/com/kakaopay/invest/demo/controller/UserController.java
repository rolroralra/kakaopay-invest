package com.kakaopay.invest.demo.controller;

import com.google.common.base.Preconditions;
import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.controller.dto.OrderDto;
import com.kakaopay.invest.demo.controller.dto.UserDto;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.service.OrderItemService;
import com.kakaopay.invest.demo.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kakaopay.invest.demo.controller.dto.ApiResult.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, OrderItemService orderItemService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResult<List<UserDto>> getAllUsers(Pageable pageable) {
        Page<User> page = userService.findAll(pageable);

        return succeed(
                page.get().map(UserDto::new).collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/{userId}")
    public ApiResult<UserDto> getUserInfo(@PathVariable Long userId) {
        User user = userService.findById(userId);

        if (Objects.isNull(user)) {
            return (ApiResult<UserDto>) failed(String.format("User is not exists. [ID=%s]", userId));
        }

        return succeed(new UserDto(user));
    }

}
