package com.kakaopay.invest.demo.model;

import com.kakaopay.invest.demo.util.DateTimeUtil;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

//@Entity
//@Table(name = "INVESTMENT_PRODUCT")
@Data
public class Product implements Cloneable {
//    public static final String DATE_TIME_REGEX_FORMAT = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])) ((0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1}))";
//    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /*
        ID                      bigint                          NOT NULL AUTO_INCREMENT,
        TITLE                   varchar(50)                     NOT NULL,
        TOTAL_INVESTING_AMOUNT  bigint                          NOT NULL,
        STARTED_AT              datetime                        NOT NULL,
        FINISHED_AT             datetime                        NOT NULL,
        STATE                   enum('PROCEEDING', 'COMPLETED') NOT NULL
     */
//    @Id
//    @Column(name = "ID")
    private Long id;

//    @Column(name = "TITLE")
    private String title;
//    @Column(name = "TOTAL_INVESTING_AMOUNT")
    private Long totalInvestingAmount;
//    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;
//    @Column(name = "FINISHED_AT")
    private LocalDateTime finishedAt;

//    @Column(name = "STATE")
//    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        PROCEEDING, COMPLETED
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
        this.state = State.PROCEEDING;

        checkArgument(id > 0L, "ID should be positive [%s]", id);
        checkArgument(totalInvestingAmount > 0L, "Total Investing Amount should be positive [%s]", totalInvestingAmount);
        checkArgument(startedAt.compareTo(finishedAt) < 0, "StartedAt: %s, FinishedAt: %s", startedAt.format(DateTimeUtil.DATE_TIME_FORMATTER), finishedAt.format(DateTimeUtil.DATE_TIME_FORMATTER));
    }

    public boolean isProceeding() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(this.startedAt) && now.isBefore(this.finishedAt) && state == State.PROCEEDING;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
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
}
