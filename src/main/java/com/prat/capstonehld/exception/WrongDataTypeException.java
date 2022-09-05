package com.prat.capstonehld.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WrongDataTypeException extends RuntimeException{
    String errMessage;
    String errCode;
}
