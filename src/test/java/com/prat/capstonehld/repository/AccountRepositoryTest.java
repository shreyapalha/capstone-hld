package com.prat.capstonehld.repository;

import com.prat.capstonehld.modal.Account;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;



import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void getAllAccounts() {
        List<Account> accounts = Arrays.asList(
                new Account(1,2500.50,1000,2000),
                new Account(1,25000.50,2000,3000)
        );
        Iterable<Account> allAccount=accountRepository.saveAll(accounts);
        AtomicInteger validIdFound=new AtomicInteger();
        allAccount.forEach(account-> {
                validIdFound.getAndIncrement();

        });
        assertThat(validIdFound.intValue()).isEqualTo(2);
    }
}