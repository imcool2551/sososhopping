package com.sososhopping.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        this("unauthorized");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(Throwable cause) {
        super(cause);
    }
}
