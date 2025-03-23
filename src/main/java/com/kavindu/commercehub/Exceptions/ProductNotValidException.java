package com.kavindu.commercehub.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotValidException extends RuntimeException {
    public ProductNotValidException() {
        super(ErrorMessages.PRODUCT_NOT_VALID.getMessage());
    }
}
