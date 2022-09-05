package com.prat.capstonehld.exception;

public class UsernameDoesntExistException extends RuntimeException{
    public UsernameDoesntExistException(String msg)
    {
        super(msg);
    }
}
