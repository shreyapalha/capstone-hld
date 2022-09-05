package com.prat.capstonehld.service;

import com.prat.capstonehld.dto.UserDto;
import com.prat.capstonehld.exception.MyValidationException;
import com.prat.capstonehld.exception.UsernameAndBankIdNotUniqueException;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.User;
import com.prat.capstonehld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired public UserRepository userRepository;
    @Autowired public AccountService accountService;
    private long bankId;

    @Transactional
    public boolean saveDto(UserDto userDto) {
        long bankId= Long.parseLong(userDto.getBankId());
        Account account=new Account(userDto.getAccountDto(), bankId);
        accountService.addAccount(account);
        User user=new User(userDto,account);
        try
        {
            userRepository.save(user);
        }catch (DataIntegrityViolationException ex)
        {
            throw new MyValidationException("Bank Id and username is not unique","ER0011");
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] str=username.split("-",2);
        String validUsername=str[0];
        String bankId=str[1];
        User user=userRepository.loadUserByUsernameAndBankId(validUsername, Long.parseLong(bankId));
        if(user==null)
            throw new UsernameNotFoundException(username);
        org.springframework.security.core.userdetails.User signInUser = new org.springframework.security.core.userdetails.User(username, user.getPassword(),new ArrayList<>());
        return signInUser;
    }

    public boolean existsUserExistsInBankId(String username, long bankId) {
        if(!userRepository.existsUsername(username))
            throw new MyValidationException("Username not found", "E0001");
        if(!userRepository.existsBankId(bankId))
            throw new MyValidationException("Bank Id doesn't exist", "E0002");
        String msg="Username doesn't exist in BankId:"+bankId;
        if(!userRepository.usernameExistWithBankId(username,bankId))
            throw new MyValidationException(msg, "E0003");
        return false;
    }

}
