package com.kavindu.commercehub.Exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespose handleProductVaildException(ProductNotFoundException exception){
        return new ErrorRespose(exception.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespose handlerProductnotVaildConstrints(ConstraintViolationException exception){
        return new ErrorRespose(exception.getConstraintViolations().iterator().next().getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorRespose handleProductNotFoundException(ProductNotFoundException exception){
        return new ErrorRespose(exception.getMessage());
    }
}


