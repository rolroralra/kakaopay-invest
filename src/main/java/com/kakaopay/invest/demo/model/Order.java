package com.kakaopay.invest.demo.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Data
public class Order implements Cloneable {
    private Long id;
    private User user;
    private List<OrderItem> items;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private State state;

    public enum State {
        PROCEED, COMPLETE, CANCELED
    }

    public Order(long id, User user, OrderItem... orderItems) {
        this(id, user, LocalDateTime.now(), null, State.PROCEED, orderItems);
    }

    public Order(long id, User user, LocalDateTime startedAt, LocalDateTime finishedAt, State state, OrderItem... orderItems) {
        this.setId(id);
        this.setUser(user);
        this.setStartedAt(startedAt);
        this.setFinishedAt(finishedAt);
        this.setState(state);
        this.setItems(Arrays.asList(orderItems));

        checkArgument(id > 0L, "ID should be positive [%s]", id);
        checkArgument(Objects.nonNull(user), "User should not be null");
        checkArgument(Objects.nonNull(startedAt), "StartedAt should not be null date");
//        checkArgument(Objects.nonNull(finishedAt), "FinishedAt should not be null date");
        checkArgument(Objects.nonNull(state), "State should not be null");
//        checkArgument(startedAt.compareTo(finishedAt) < 0, "StartedAt: %s, FinishedAt: %s", startedAt.format(DateTimeUtil.DATE_TIME_FORMATTER), finishedAt.format(DateTimeUtil.DATE_TIME_FORMATTER));
    }

    public static Order of(long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        return new Order(orderId, new User(userId, userName, userEmail), new OrderItem(orderItemId, new Product(productId, productTitle, productAmount), orderItemAmount));
    }

    public static Order of(Order order) throws CloneNotSupportedException {
        return (Order) order.clone();
    }

    public void addItem(OrderItem ...orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (Objects.nonNull(orderItem) &&
                    items.stream().noneMatch(item -> item.equalsId(orderItem))) {
                this.items.add(orderItem);
            }
        }
    }

    public void complete() {
        setState(State.COMPLETE);
        setFinishedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && user.equals(order.user) && items.equals(order.items) && startedAt.equals(order.startedAt) && Objects.equals(finishedAt, order.finishedAt) && state == order.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, items, startedAt, finishedAt, state);
    }
}
