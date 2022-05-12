package com.sososhopping.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePhoneException extends RuntimeException {

    public DuplicatePhoneException() {
        this("이미 사용중인 번호입니다");
    }

    public DuplicatePhoneException(String message) {
        super(message);
    }

    public DuplicatePhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatePhoneException(Throwable cause) {
        super(cause);
    }
}
