package com.sososhopping.domain.store.exception;

public class DuplicateBusinessNumberException extends RuntimeException {

    public DuplicateBusinessNumberException() {
    }

    public DuplicateBusinessNumberException(String message) {
        super(message);
    }

    public DuplicateBusinessNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateBusinessNumberException(Throwable cause) {
        super(cause);
    }
}
