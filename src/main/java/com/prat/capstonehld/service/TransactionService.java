package com.prat.capstonehld.service;

import com.prat.capstonehld.dto.TransactionDBDto;
import com.prat.capstonehld.dto.TransactionDto;
import com.prat.capstonehld.modal.Transactions;
import com.prat.capstonehld.repository.AccountRepository;
import com.prat.capstonehld.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired private TransactionsRepository transactionsRepository;
    @Autowired private AccountRepository accountRepository;

    public void addData(TransactionDto transactionDto)
    {
        Transactions transactions=new Transactions();
        transactions.setToAccount(accountRepository.findById(transactionDto.getToAccountId()).get());
        transactions.setFromAccount(accountRepository.findById(transactionDto.getFromAccountId()).get());
        transactions.setDateOfTransaction(new Date());
        transactionsRepository.save(transactions);
    }

    public TransactionDto getData(long id)
    {
        Optional<Transactions> transactions=transactionsRepository.findById(id);
        TransactionDto transactionDto=new TransactionDto();
        transactionDto.setTransactionAmount(transactions.get().getTransactionAmount());
        transactionDto.setCur(transactions.get().getCur());
        transactionDto.setBankId(transactions.get().getBankId());
        transactionDto.setFromAccountId(transactions.get().getFromAccount().getId());
        transactionDto.setToAccountId(transactions.get().getToAccount().getId());
        return transactionDto;
    }

    public List<TransactionDBDto> getAllTransaction(long id) {
        return transactionsRepository.getAllTransaction(id);
    }
}
