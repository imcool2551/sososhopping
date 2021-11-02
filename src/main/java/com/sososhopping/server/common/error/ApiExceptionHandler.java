package com.sososhopping.server.common.error;

import com.sososhopping.server.domain.responseDto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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

    @ExceptionHandler(Api409Exception.class)
    public ResponseEntity api409Exception(HttpServletRequest request, final Api409Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }

}
