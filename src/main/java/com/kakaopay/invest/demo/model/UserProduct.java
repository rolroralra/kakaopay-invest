package com.kakaopay.invest.demo.model;

import lombok.Getter;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class UserProduct {
    private final Long id;
    private final User user;
    private final Product product;
    private final String title;
    private final Long totalProductInvestAmount;
    private Long totalUserInvestAmount;
    private LocalDateTime finishedAt;

    public UserProduct(OrderItem orderItem) {
        id = orderItem.getProduct().getId();
        user = orderItem.getOrder().getUser();
        product = orderItem.getProduct();
        title = orderItem.getProduct().getTitle();
        totalProductInvestAmount = orderItem.getProduct().getTotalInvestingAmount();
        totalUserInvestAmount = orderItem.getAmount();
        finishedAt = orderItem.getOrder().getFinishedAt();
    }

    public void addUserAmount(Long amount) {
        totalUserInvestAmount += amount;
    }

    public void updateDate(LocalDateTime finishedAt) {
        checkNotNull(finishedAt);

        if (this.finishedAt.isBefore(finishedAt)) {
            this.finishedAt = finishedAt;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProduct{");
        sb.append(", userId=").append(user.getId());
        sb.append(", product=").append(product);
        sb.append(", title='").append(title).append('\'');
        sb.append(", totalProductInvestAmount=").append(totalProductInvestAmount);
        sb.append(", totalUserInvestAmount=").append(totalUserInvestAmount);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append('}');
        return sb.toString();
    }
}
