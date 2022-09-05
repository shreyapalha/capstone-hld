package com.prat.capstonehld.modal;//package com.pratishthan.HLD_capstonemercury.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_account_id",nullable = false)
    @JsonBackReference
    private Account toAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_Account_id",nullable = false)
    @JsonBackReference
    private  Account fromAccount;
    @Column(name="transaction_amount",nullable = false)
    private double transactionAmount;
    @Column(name="transaction_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateOfTransaction;
    @Column(name="transaction_currency",nullable = true)
    private String cur;
    @Column(name="bank_id",nullable = false)
    private long bankId;

    public Transactions( Account toAccount, Account fromAccount, double transactionAmount, Date dateOfTransaction, String cur, long bankId) {
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.transactionAmount = transactionAmount;
        this.dateOfTransaction = dateOfTransaction;
        this.cur = cur;
        this.bankId = bankId;
    }



    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + transactionId +
                ", toAccount=" + toAccount +
                ", fromAccount=" + fromAccount +
                ", transactionAmount=" + transactionAmount +
                ", dateOfTransaction=" + dateOfTransaction +
                ", cur='" + cur + '\'' +
                ", bankId=" + bankId +
                '}';
    }
}
