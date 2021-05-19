package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import com.kakaopay.invest.demo.controller.dto.OrderDto;
import com.kakaopay.invest.demo.controller.dto.OrderItemDto;
import com.kakaopay.invest.demo.model.OrderItem;
import com.kakaopay.invest.demo.model.Product;
import com.kakaopay.invest.demo.model.User;
import com.kakaopay.invest.demo.service.OrderItemService;
import com.kakaopay.invest.demo.service.OrderService;
import com.kakaopay.invest.demo.service.ProductService;
import com.kakaopay.invest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        if (product.isCompleted()) {
            return (ApiResult<OrderDto>) failed(String.format("Product is end of sale. [FINISHED_AT=%s]", product.getFinishedAt()));
        }

        if (product.isSoldOut()) {
            return (ApiResult<OrderDto>) failed(String.format("Product is sold out. [TOTAL_MOUNT=%s]", product.getTotalInvestingAmount()));
        }

        if (Objects.isNull(amount) || !product.isSellable(amount)) {
            return (ApiResult<OrderDto>) failed(String.format("Amount is overflowed [yourAmount: %s, maxPossibleAmount: %s]", amount, product.getPossibleAmount()));
        }

        OrderItem orderItem = orderItemService.createOrderItem(productId, amount);

        if (Objects.isNull(orderItem)) {
            return (ApiResult<OrderDto>) failed("Failed to order.");
        }

        return succeed(
                new OrderDto(orderService.takeOrder(user, orderItem))
        );
    }

    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    ApiResult<List<OrderItemDto>> getOrderItemList(@RequestHeader(name = "X-USER-ID") Long userId) {
        User user = userService.findById(userId);

        if (Objects.isNull(user)) {
            return (ApiResult<List<OrderItemDto>>) failed(String.format("User is not exists. [ID=%s]", userId));
        }

        return succeed(
                orderItemService.findOrderItemsByUserId(userId).stream().map(OrderItemDto::new).collect(Collectors.toList())
        );
    }
}
