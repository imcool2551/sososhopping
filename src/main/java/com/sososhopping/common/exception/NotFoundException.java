package com.sososhopping.common.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
