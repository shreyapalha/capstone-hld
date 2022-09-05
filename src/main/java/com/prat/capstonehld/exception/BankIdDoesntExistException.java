package com.prat.capstonehld.exception;

public class BankIdDoesntExistException extends RuntimeException{

    public BankIdDoesntExistException(String msg)
    {
        super(msg);
    }
}
