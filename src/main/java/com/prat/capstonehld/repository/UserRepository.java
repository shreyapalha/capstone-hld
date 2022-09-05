package com.prat.capstonehld.repository;//package com.pratishthan.HLD_capstonemercury.repository;

import com.prat.capstonehld.dto.SignInIntDto;
import com.prat.capstonehld.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Object> {

    @Query("SELECT u FROM User u WHERE u.username=?1 and u.bankId=?2")
    User loadUserByUsernameAndBankId(String name,long bankId);

//    @Query("SELECT u FROM User u WHERE u.username=?1")
//    List<User> existsUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username=?1")
    boolean existsUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM User u WHERE u.bankId=?1")
    boolean existsBankId(long bankId);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username=?1 and u.bankId=?2")
    boolean usernameExistWithBankId(String username, long bankId);
}
