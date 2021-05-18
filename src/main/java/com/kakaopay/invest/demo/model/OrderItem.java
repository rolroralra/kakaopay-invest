package com.kakaopay.invest.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name = "INVEST_ORDER_ITEM")
@Getter
@Setter
public class OrderItem implements Cloneable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID", foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_TO_PRODUCT"))
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "ORDER_ID", foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_TO_ORDER"))
    private Order order;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        PROCEED, SUCCESS, FAILED
    }

    public OrderItem() {
        this(null, null, 0L);
    }

    public OrderItem(Product product, long amount) {
        this(null, product, amount);
    }

    public OrderItem(Long id, Product product, long amount) {
        this(id, product, amount, State.PROCEED);
    }

    public OrderItem(Long id, Product product, long amount, State state) {
        this.setId(id);
        this.setProduct(product);
        this.setAmount(amount);
        this.setState(state);

        checkArgument(Objects.requireNonNullElse(id, 0L) >= 0L, "ID should be positive [%s]", id);
//        checkArgument(Objects.nonNull(product), "Product should not be null");
        checkArgument(amount >= 0L, "Amount should be positive [%s]", amount);
        checkArgument(Objects.nonNull(state), "State should not be null");
    }

    public boolean equalsId(OrderItem orderItem) {
        return this.getId().equals(orderItem.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id.equals(orderItem.id) && product.equals(orderItem.product) && amount.equals(orderItem.amount) && state == orderItem.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, amount, state);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("id=").append(id);
        sb.append(", product=").append(product);
        sb.append(", amount=").append(amount);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
