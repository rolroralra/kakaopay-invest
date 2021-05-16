package com.kakaopay.invest.demo.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Data
public class OrderItem implements Cloneable {
    private Long id;
    private Product product;
    private Long amount;
    private State state;

    public enum State {
        NOT_COMPLETED, SUCCESS, FAILED
    }

    public OrderItem(long id, Product product, long amount) {
        this(id, product, amount, State.NOT_COMPLETED);
    }

    public OrderItem(long id, Product product, long amount, State state) {
        this.setId(id);
        this.setProduct(product);
        this.setAmount(amount);
        this.setState(state);

        checkArgument(id > 0L, "ID should be positive [%s]", id);
        checkArgument(Objects.nonNull(product), "Product should not be null");
        checkArgument(amount > 0L, "Amount should be positive [%s]", amount);
        checkArgument(Objects.nonNull(state), "State should not be null");
    }

    public boolean equalsId(OrderItem orderItem) {
        return this.getId().equals(orderItem.getId());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
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
}
