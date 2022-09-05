package com.prat.capstonehld.repository;//package com.pratishthan.HLD_capstonemercury.repository;
//
import com.prat.capstonehld.dto.TransactionDBDto;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {



    @Query(value = "SELECT transaction_id as transactionId,to_account_id as toAccountId,from_account_id as fromAccountId,transaction_amount as transactionAmount,transaction_date as transactionDate,transaction_currency as transactionCurrency FROM transaction WHERE to_account_id=:id or from_account_id=:id",nativeQuery = true)
    List<TransactionDBDto> getAllTransaction(long id);
}
