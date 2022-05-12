package com.sososhopping.common.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
