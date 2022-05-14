package com.encore.byebuying.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderHistorySearch {
    private String user;
    private LocalDate startDate;
    private LocalDate endDate;
}
