package com.sososhopping.common.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        this("unauthorized");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

}
