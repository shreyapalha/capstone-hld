package com.prat.capstonehld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

//    private long id;
    @Digits(integer = 10,fraction = 0,message = "Value should be long")
    private String bankId;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
            message = "username must be of 6 to 12 length with no special characters")
    private String username;
    @NotEmpty
    @Size(min=2,message = "Password should have atleast 2 character ")
    private String password;
    private AccountDto accountDto;

}
