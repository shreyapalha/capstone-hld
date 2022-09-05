package com.prat.capstonehld.service;

import com.prat.capstonehld.dto.FundTransferDto;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.Transactions;
import com.prat.capstonehld.repository.AccountRepository;
import com.prat.capstonehld.repository.TransactionsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {


    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private Account account2;
    private FundTransferDto fundTransferDto;


    @BeforeEach
    void setUp() {
        System.out.println("Before");
        account=Account.builder()
                .id(12)
                .bankId(1)
                .balance(40000)
                .lienAmount(1000)
                .limitBalance(1000)
                .build();

        account2=Account.builder()
                .id(13)
                .bankId(1)
                .balance(50000)
                .lienAmount(2000)
                .limitBalance(1000)
                .build();

    }

    @AfterEach
    void tearDown() {
        System.out.println("After");
    }

    @DisplayName("Junit test for adding account")
    @Test
    void addAccount() {

        given(accountRepository.save(account)).willReturn(account);

        // when -  action or the behaviour that we are going test
        Account savedAccount = accountService.addAccount(account);

        // then - verify the output
        assertThat(savedAccount).isNotNull();
    }

    @DisplayName("Junit test for balance enquiry")
    @Test
    void balanceEnquiry() {
        given(accountRepository.findById(12L)).willReturn(Optional.of(account));

        double balance= accountService.balanceEnquiry(account.getId());

        assertThat(balance).isEqualTo(account.getBalance());
    }

    @DisplayName("Junit test for funds Transfer")
    @Test
    void fundsTransfer() {
        given(accountRepository.findById(12L)).willReturn(Optional.of(account));
        given(accountRepository.findById(13L)).willReturn(Optional.of(account2));
        fundTransferDto=FundTransferDto.builder()
                .sourceAccountNumber(account.getId())
                .destAccountNumber(account2.getId())
                .amount(100)
                .build();


        given(accountRepository.existsById(account.getId())).willReturn(true);
        given(accountRepository.existsById(account2.getId())).willReturn(true);
        boolean fundTransfer=accountService.fundsTransfer(fundTransferDto);
        assertThat(fundTransfer).isTrue();


    }
}