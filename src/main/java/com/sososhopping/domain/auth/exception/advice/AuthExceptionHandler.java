package com.sososhopping.domain.auth.exception.advice;

import com.sososhopping.a.dto.ErrorResponse;
import com.sososhopping.domain.auth.exception.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<ErrorResponse> invalidCredentialsExceptionHandler(
            HttpServletRequest request, InvalidCredentialsException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
    }
}
