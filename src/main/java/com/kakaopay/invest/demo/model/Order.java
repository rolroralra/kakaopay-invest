package com.kakaopay.invest.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name = "INVEST_ORDER")
@Getter
@Setter
public class Order implements Cloneable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_ORDER_TO_USER"))
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<OrderItem> items;

    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;

    @Column(name = "FINISHED_AT")
    private LocalDateTime finishedAt;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        PROCEED, COMPLETE, CANCELED
    }

    public Order() {
        this(null);
    }

    public Order(User user, OrderItem... orderItems) {
        this(null, user, orderItems);
    }

    public Order(Long id, User user, OrderItem... orderItems) {
        this(id, user, LocalDateTime.now(), null, State.PROCEED, orderItems);
    }

    public Order(Long id, User user, LocalDateTime startedAt, LocalDateTime finishedAt, State state, OrderItem... orderItems) {
        this.setId(id);
        this.setUser(user);
        this.setStartedAt(startedAt);
        this.setFinishedAt(finishedAt);
        this.setState(state);
        this.setItems(new ArrayList<>());

        addItem(orderItems);

        checkArgument(Objects.requireNonNullElse(id, 0L) >= 0L, "ID should be positive [%s]", id);
        checkArgument(Objects.nonNull(startedAt), "StartedAt should not be null date");
        checkArgument(Objects.nonNull(state), "State should not be null");

//        checkArgument(Objects.nonNull(finishedAt), "FinishedAt should not be null date");
//        checkArgument(startedAt.compareTo(finishedAt) < 0, "StartedAt: %s, FinishedAt: %s", startedAt.format(DateTimeUtil.DATE_TIME_FORMATTER), finishedAt.format(DateTimeUtil.DATE_TIME_FORMATTER));
    }

    public static Order of(Long orderId, long userId, String userName, String userEmail, long orderItemId, long productId, String productTitle, long productAmount, long orderItemAmount) {
        return new Order(orderId, new User(userId, userName, userEmail), new OrderItem(orderItemId, new Product(productId, productTitle, productAmount), orderItemAmount));
    }

    public static Order of(Order order) throws CloneNotSupportedException {
        return (Order) order.clone();
    }

    public void addItem(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (Objects.nonNull(orderItem) &&
                    items.stream().noneMatch(item -> item.equalsId(orderItem))) {
                this.items.add(orderItem);
                orderItem.setOrder(this);
            }
        }
    }

    public void addItem(OrderItem ...orderItems) {
       addItem(List.of(orderItems));
    }

    public void complete() {
        setState(State.COMPLETE);
        setFinishedAt(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && Objects.equals(startedAt, order.startedAt) && Objects.equals(finishedAt, order.finishedAt) && state == order.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, startedAt, finishedAt, state);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", items=").append(items);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
