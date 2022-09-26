package com.prat.capstonehld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
//    private long account_id;

    private double balance_amount;
    private double limit_amount;
    private double lien_amount;
}
