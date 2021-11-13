package com.sososhopping.server.common.error;

import com.sososhopping.server.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice(basePackages = "com.sososhopping.server.controller")
public class ApiExceptionHandler {

    @ExceptionHandler(Api400Exception.class)
    public ResponseEntity api400Exception(HttpServletRequest request, final Api400Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Api401Exception.class)
    public ResponseEntity api401Exception(HttpServletRequest request, final Api401Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Api404Exception.class)
    public ResponseEntity api404Exception(HttpServletRequest request, final Api404Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Api409Exception.class)
    public ResponseEntity api409Exception(HttpServletRequest request, final Api409Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Api500Exception.class)
    public ResponseEntity api500Exception(HttpServletRequest request, final Api500Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(HttpServletRequest request, final ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }
}
