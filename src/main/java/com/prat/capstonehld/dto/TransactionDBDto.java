package com.prat.capstonehld.dto;

import java.util.Date;

public interface TransactionDBDto {

    long getTransactionId();
    long getToAccountId();
    long getFromAccountId();
    double getTransactionAmount();
    Date getTransactionDate();
    String getTransactionCurrency();
}
