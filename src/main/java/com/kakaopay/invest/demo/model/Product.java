package com.kakaopay.invest.demo.model;

import com.kakaopay.invest.demo.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name = "INVEST_PRODUCT")
@Getter
@Setter
public class Product implements Cloneable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TOTAL_AMOUNT")
    private Long totalInvestingAmount;

    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;

    @Column(name = "FINISHED_AT")
    private LocalDateTime finishedAt;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        PROCEED, COMPLETED
    }

    public Product() {
        this(null, "", 0L);
    }

    public Product(Long id, String title, Long totalInvestingAmount) {
        this(id, title, totalInvestingAmount, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }

    public Product(Long id, String title, Long totalInvestingAmount, String startedAt, String finishedAt) {
        this(id, title, totalInvestingAmount, LocalDateTime.parse(startedAt, DateTimeUtil.DATE_TIME_FORMATTER), LocalDateTime.parse(finishedAt, DateTimeUtil.DATE_TIME_FORMATTER));
    }

    public Product(Long id, String title, Long totalInvestingAmount, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.id = id;
        this.title = title;
        this.totalInvestingAmount = totalInvestingAmount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.state = State.PROCEED;

        checkArgument(Objects.requireNonNullElse(id, 0L) >= 0L, "ID should be positive [%s]", id);
        checkArgument(totalInvestingAmount >= 0L, "Total Investing Amount should be positive [%s]", totalInvestingAmount);
        checkArgument(startedAt.compareTo(finishedAt) < 0, "StartedAt: %s, FinishedAt: %s", startedAt.format(DateTimeUtil.DATE_TIME_FORMATTER), finishedAt.format(DateTimeUtil.DATE_TIME_FORMATTER));
    }

    public boolean isProceeding() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(this.startedAt) && now.isBefore(this.finishedAt) && state == State.PROCEED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && title.equals(product.title) && totalInvestingAmount.equals(product.totalInvestingAmount) && startedAt.equals(product.startedAt) && finishedAt.equals(product.finishedAt) && state == product.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, totalInvestingAmount, startedAt, finishedAt, state);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", totalInvestingAmount=").append(totalInvestingAmount);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", finishedAt=").append(finishedAt);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
