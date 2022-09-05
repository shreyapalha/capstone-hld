package com.prat.capstonehld.repository;

import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Java6Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {

    @Autowired AccountRepository accountRepository;
    @Autowired UserRepository userRepository;

    @BeforeEach
    void setUp() {
        System.out.println("before");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after");
       userRepository.deleteAll();
      accountRepository.deleteAll();

    }

    @Test
    void loadUserByUsernameAndBankId() {
        Account acc1=new Account(1,50000,2000,2000);
        accountRepository.save(acc1);
        User user=new User("user","root",1,acc1);
        userRepository.save(user);
        User newUser=userRepository.loadUserByUsernameAndBankId("user",1);
        boolean res=false;
        if(newUser.getId()== user.getId())
            res=true;
        assertThat(res).isTrue();
    }

    @Test
    void existsUsername() {
        Account acc1=new Account(1,50000,2000,2000);
        accountRepository.save(acc1);
        Account acc2=new Account(1,30000,1000,3000);
        accountRepository.save(acc2);
        User user=new User("user","root",1,acc1);
        userRepository.save(user);
        boolean res=userRepository.existsUsername(user.getUsername());
        assertThat(res).isTrue();
    }

    @Test
    void existsBankId() {
        Account acc1=new Account(1,50000,2000,2000);
        accountRepository.save(acc1);
        Account acc2=new Account(1,30000,1000,3000);
        accountRepository.save(acc2);
        User user=new User("user","root",1,acc1);
        userRepository.save(user);
        boolean res=userRepository.existsBankId(user.getBankId());
        assertThat(res).isTrue();
    }

    @Test
    void usernameExistWithBankId() {
        Account acc1=new Account(1,50000,2000,2000);
        accountRepository.save(acc1);
        Account acc2=new Account(1,30000,1000,3000);
        accountRepository.save(acc2);
        User user=new User("user","root",1,acc1);
        userRepository.save(user);
        boolean res=userRepository.usernameExistWithBankId(user.getUsername(),user.getBankId());
        assertThat(res).isTrue();
    }
}