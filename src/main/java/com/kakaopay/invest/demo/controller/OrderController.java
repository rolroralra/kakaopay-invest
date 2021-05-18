package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.controller.dto.OrderDto;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.service.OrderItemService;
import com.kakaopay.invest.demo.service.OrderService;
import com.kakaopay.invest.demo.service.ProductService;
import com.kakaopay.invest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.kakaopay.invest.demo.controller.dto.ApiResult.failed;
import static com.kakaopay.invest.demo.controller.dto.ApiResult.succeed;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ProductService productService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    ApiResult<OrderDto> takeOrder(@RequestHeader(name = "X-USER-ID") Long userId, @RequestParam Long productId, @RequestParam Long amount) {
        User user = userService.findById(userId);
        Product product = productService.findById(productId);

        if (Objects.isNull(user)) {
            return (ApiResult<OrderDto>) failed(String.format("User is not exists. [ID=%s]", userId));
        }

        if (Objects.isNull(product)) {
            return (ApiResult<OrderDto>) failed(String.format("Product is not exists. [ID=%s]", productId));
        }

        if (Objects.isNull(amount) || amount > product.getPossibleAmount()) {
            return (ApiResult<OrderDto>) failed(String.format("Amount is overflowed [yourAmount: %s, maxPossibleAmount: %s]", amount, product.getPossibleAmount()));
        }

        OrderItem orderItem = orderItemService.createOrderItem(product, amount);

        return succeed(
                new OrderDto(orderService.takeOrder(user, orderItem))
        );
    }
}
