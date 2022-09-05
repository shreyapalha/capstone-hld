package com.prat.capstonehld.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.prat.capstonehld.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="bank_id",nullable = false)
    private long bankId;
    @Column(name="balance_amount",nullable = false)
    private double balance;
    @Column(name="limit_amount")
    private double limitBalance;
    @Column(name="lien_amount")
    private double lienAmount;

    @OneToOne(mappedBy = "account")
    @JsonManagedReference
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "toAccount",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transactions> transactionsList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "fromAccount",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transactions> transactionsList2;

    public Account(AccountDto accountDto,long bankId)
    {
//        this.id=accountDto.getAccount_id();
        this.balance=accountDto.getBalance_amount();
        this.lienAmount= accountDto.getLien_amount();
        this.limitBalance= accountDto.getLimit_amount();
        this.bankId=bankId;

    }

    public Account(long bankId, double balance, double limitBalance, double lienAmount) {
        this.bankId = bankId;
        this.balance = balance;
        this.limitBalance = limitBalance;
        this.lienAmount = lienAmount;
    }

    public Account(long id, long bankId, double balance, double limitBalance, double lienAmount) {
        this.id = id;
        this.bankId = bankId;
        this.balance = balance;
        this.limitBalance = limitBalance;
        this.lienAmount = lienAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", bankId=" + bankId +
                ", balance=" + balance +
                ", limitBalance=" + limitBalance +
                ", lienAmount=" + lienAmount +
                '}';
    }
}
