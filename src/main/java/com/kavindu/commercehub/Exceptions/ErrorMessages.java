package com.kavindu.commercehub.Exceptions;

public enum ErrorMessages {
    PRODUCT_NOT_FOUND("Product not Found")
    ,PRICE_CANT_BE_NEGATIVE("price cant negative")
    ,DESCRPTION_LENGTH("legth is more")
    ,MANUFACTURE_EMPTY("Empty")
    ,PRODUCT_NOT_VALID("enter valid credentials")
    ,USER_NOT_FOUND("User not Found")
    ;

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
