package com.sososhopping.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException]", e);

        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlException(SQLException e) {
        log.error("[SQLException]", e);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("database error"));
    }
}
