package com.sososhopping.domain.owner.exception;

public class MissingFileException extends RuntimeException {

    public MissingFileException() {
    }

    public MissingFileException(String message) {
        super(message);
    }

    public MissingFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFileException(Throwable cause) {
        super(cause);
    }
}
