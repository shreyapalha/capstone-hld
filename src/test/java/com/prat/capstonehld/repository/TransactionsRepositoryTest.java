package com.prat.capstonehld.repository;

import com.prat.capstonehld.dto.TransactionDBDto;
import com.prat.capstonehld.dto.TransactionDto;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.Transactions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionsRepositoryTest {

    @Autowired TransactionsRepository transactionsRepository;
    @Autowired AccountRepository accountRepository;

    @BeforeEach
    void setUp() {

        System.out.println("Before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after test");
    }

    @Test
    void getAllTransaction() {

        Account acc1=new Account(1,50000,2000,2000);
        Account acc2=new Account(1,30000,1000,3000);
        accountRepository.save(acc1);
        accountRepository.save(acc2);
        Transactions transaction1=new Transactions(acc1,acc2,50.00,new Date(),"USD",1);
        Transactions transaction2=new Transactions(acc2,acc1,40.00,new Date(),"USD",1);
        transactionsRepository.save(transaction1);
        transactionsRepository.save(transaction2);

        Iterable<TransactionDBDto> transactionDBDto= transactionsRepository.getAllTransaction(48);
        AtomicInteger countofTransaction=new AtomicInteger();
        transactionDBDto.forEach(transaction->{
            if(transaction.getTransactionId()>0)
                countofTransaction.getAndIncrement();
        });
        assertThat(countofTransaction.intValue()).isEqualTo(2);
    }
}