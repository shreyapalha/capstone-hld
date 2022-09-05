package com.prat.capstonehld.exception;

public class UsernameAndBankIdNotUniqueException extends RuntimeException{
    public UsernameAndBankIdNotUniqueException(String message) {
        super(message);
    }
}
