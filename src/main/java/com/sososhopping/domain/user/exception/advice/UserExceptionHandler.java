package com.sososhopping.domain.user.exception.advice;

import com.sososhopping.common.ErrorResponse;
import com.sososhopping.domain.user.exception.DuplicatePhoneException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({DuplicatePhoneException.class})
    public ResponseEntity<ErrorResponse> duplicatePhoneExceptionHandler(
            HttpServletRequest request, DuplicatePhoneException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }
}
