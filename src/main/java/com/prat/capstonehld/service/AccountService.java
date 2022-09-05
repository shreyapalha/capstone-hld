package com.prat.capstonehld.service;


import com.prat.capstonehld.dto.AccountDBDto;
import com.prat.capstonehld.dto.FundTransferDto;
import com.prat.capstonehld.exception.MyValidationException;
import com.prat.capstonehld.exception.WrongDataTypeException;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.Transactions;
import com.prat.capstonehld.repository.AccountRepository;
import com.prat.capstonehld.repository.TransactionsRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionsRepository transactionsRepository;
    private long aLong;

    public Account addAccount(Account account){
        return accountRepository.save(account);

    }

    public double balanceEnquiry(long id){


        if(id!=(long)id)
            throw new WrongDataTypeException("Id should be of long type","ER005");
        try{
            Optional<Account> account=accountRepository.findById(id);
            return account.get().getBalance();
        }catch (Exception e)
        {
            throw new MyValidationException("Id doesn't exist","ER006");
        }

    }

    @Transactional
    public boolean fundsTransfer(FundTransferDto fundTransferDto)
    {
        long sourceId=fundTransferDto.getSourceAccountNumber();
        long destId=fundTransferDto.getDestAccountNumber();
        if(sourceId==destId)
            throw new MyValidationException("Source id and destination id should not be same","ER007");
        double amount=fundTransferDto.getAmount();
        if(!accountRepository.existsById(sourceId))
            throw new MyValidationException("Source Id doesn't exist","ER008");
        if(!accountRepository.existsById(destId))
            throw new MyValidationException("Destination Id doesn't exist","ER009");
        Optional<Account> sAcc=accountRepository.findById(sourceId);
        Optional<Account> dAcc=accountRepository.findById(destId);

        if(sAcc.get().getBankId()!=dAcc.get().getBankId())
            throw new MyValidationException("Bank id is different","ER010");
        double usableBalance=sAcc.get().getBalance()+sAcc.get().getLimitBalance()-sAcc.get().getLienAmount();
        if(usableBalance<amount)
            throw new MyValidationException("Insufficient Balance","ER011");
        sAcc.get().setBalance(sAcc.get().getBalance()-amount);
        dAcc.get().setBalance(dAcc.get().getBalance()+amount);
        accountRepository.save(sAcc.get());
        Transactions transactions=new Transactions(sAcc.get(),dAcc.get(),amount,new Date(),"USD",sAcc.get().getBankId());
        transactionsRepository.save(transactions);
        return true;
    }

    public Account getAccount(long id) {
        return accountRepository.findById(id).get();
    }
}
