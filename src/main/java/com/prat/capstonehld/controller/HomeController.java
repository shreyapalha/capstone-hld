package com.prat.capstonehld.controller;

import com.prat.capstonehld.dto.*;
import com.prat.capstonehld.exception.WrongLongConversionException;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.service.AccountService;
import com.prat.capstonehld.service.TransactionService;
import com.prat.capstonehld.service.UserService;
import com.prat.capstonehld.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired private AccountService accountService;
    @Autowired private UserService userService;
    @Autowired private TransactionService transactionService;
    @Autowired private JWTUtility jwtUtility;
    @Autowired private AuthenticationManager authenticationManager;

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable long id)
    {
        return new ResponseEntity<>(accountService.getAccount(id),HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> addUser(@Valid @RequestBody UserDto userDto)  {

        return new ResponseEntity<>(userService.saveDto(userDto),HttpStatus.OK);
    }

    @GetMapping("/account/enquire/{id}")
    public ResponseEntity<Double> getBalanceById(@PathVariable("id") long id)
    {
        double balance=accountService.balanceEnquiry(id);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

   @PostMapping("/account/transfer")
    public ResponseEntity<Boolean> fundTransfer(@RequestBody FundTransferDto fundTransferDto)
   {

       return new ResponseEntity<>(accountService.fundsTransfer(fundTransferDto),HttpStatus.OK);
   }

   @GetMapping("/transaction/getTransaction/{id}")
    public ResponseEntity<List<TransactionDBDto>> getTransaction(@PathVariable long id)
   {
       return new ResponseEntity<>(transactionService.getAllTransaction(id),HttpStatus.OK);
   }

   @PostMapping("/authenticate")
   public ResponseEntity<JwtResponse> authenticate(@RequestBody  JwtRequest jwtRequest) throws Exception
   {
       long bankId;
       try{
           bankId=Long.parseLong(jwtRequest.getBankId());
       }catch(NumberFormatException ex)
       {
           throw new WrongLongConversionException("Id Should be of long type only");
       }
       userService.existsUserExistsInBankId(jwtRequest.getUsername(),bankId);
       String newUsername=jwtRequest.getUsername()+"-"+jwtRequest.getBankId();
       jwtRequest.setUsername(newUsername);
       System.out.println(jwtRequest.getUsername());

       try{
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           jwtRequest.getUsername(),
                           jwtRequest.getPassword()
                   )
           );
       } catch (BadCredentialsException e) {
           throw new Exception("Invalid Credentials",e);
       }

       final UserDetails userDetails=userService.loadUserByUsername(jwtRequest.getUsername());
       final String token=jwtUtility.generateToken(userDetails);

       return new ResponseEntity<>(new JwtResponse(token),HttpStatus.OK);

   }

}
