package com.prat.capstonehld.repository;//package com.pratishthan.HLD_capstonemercury.repository;
import com.prat.capstonehld.dto.AccountDBDto;
import com.prat.capstonehld.modal.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT account_id as accountId,balance_amount as balanceAmount,Lien_amount as lienAmount,limit_amount as limitAmount FROM ACCOUNT",nativeQuery = true)
    List<AccountDBDto> getAllAccounts();
}
