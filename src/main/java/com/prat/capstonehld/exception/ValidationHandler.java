package com.prat.capstonehld.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String ,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError) error).getField();
            String message=error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName().toUpperCase() + " should be of type " + ex.getRequiredType().getName();
        ErrorDetails errorDetails=new ErrorDetails(new Date(),error,request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,WebRequest request)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails,HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({MyValidationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            MyValidationException ex,WebRequest request)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getErrorMsg(),ex.getErrorCode());
        return new ResponseEntity<Object>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({WrongDataTypeException.class})
    public ResponseEntity<Object> handleWrongDataTypeException(WrongDataTypeException ex,WebRequest req)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getErrMessage(),ex.getErrCode());
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> usernameNotFoundExceptionHandler(
            UsernameNotFoundException ex,WebRequest request)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),"Username Not Found",request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialExceptionHandler(
            BadCredentialsException ex,WebRequest request)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),"Incorrect Password",request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails,HttpStatus.FORBIDDEN);
    }



    @ExceptionHandler({WrongLongConversionException.class})
    public ResponseEntity<Object> WrongLongConversionException(
            WrongLongConversionException ex,WebRequest request)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails,HttpStatus.FORBIDDEN);
    }

}
