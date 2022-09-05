package com.prat.capstonehld.exception;

import lombok.Data;

@Data
public class MyValidationException extends RuntimeException{
    String errorCode;
    String errorMsg;
    public MyValidationException(String msg,String errorCode)
    {
        this.errorCode=errorCode;
        this.errorMsg=msg;

    }
}
