package com.sososhopping.common.exception.advice;

import com.sososhopping.a.dto.ErrorResponse;
import com.sososhopping.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validationException(
            HttpServletRequest request, MethodArgumentNotValidException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler({BindingException.class, NoSuchElementException.class, BadRequestException.class})
    public ResponseEntity<ErrorResponse> validationException(
            HttpServletRequest request, Exception e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<ErrorResponse> unAuthorizedException(
            HttpServletRequest request, UnAuthorizedException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorResponse> forbiddenException(
            HttpServletRequest request, ForbiddenException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundException(
            HttpServletRequest request, NotFoundException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ErrorResponse> sqlException(HttpServletRequest request, SQLException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("something went wrong"));
    }

}
