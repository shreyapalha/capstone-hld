package com.prat.capstonehld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private long toAccountId;
    private long fromAccountId;
    private double transactionAmount;
    private String cur;
    private long bankId;
}
