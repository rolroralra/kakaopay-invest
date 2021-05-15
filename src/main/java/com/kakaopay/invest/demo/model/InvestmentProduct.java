package com.kakaopay.invest.demo.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkArgument;


@Data
public class InvestmentProduct {
    public static final String DATE_TIME_REGEX_FORMAT = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])) ((0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1}))";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private Long id;
    private String title;
    private Long totalInvestingAmount;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    public InvestmentProduct(Long id, String title, Long totalInvestingAmount, String startedAt, String finishedAt) {
        this(id, title, totalInvestingAmount, LocalDateTime.parse(startedAt, DATE_TIME_FORMATTER), LocalDateTime.parse(finishedAt, DATE_TIME_FORMATTER));
        checkArgument(isValid(), "StartedAt: %s, FinishedAt: %s", startedAt, finishedAt);
    }

    public InvestmentProduct(Long id, String title, Long totalInvestingAmount, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.id = id;
        this.title = title;
        this.totalInvestingAmount = totalInvestingAmount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public boolean isValid() {
        return startedAt.compareTo(finishedAt) < 0;
    }
}
