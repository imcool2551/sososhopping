package com.sososhopping.domain.store.exception.advice;

import com.sososhopping.common.dto.ErrorResponse;
import com.sososhopping.domain.store.exception.MissingFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler({MissingFileException.class})
    public ResponseEntity<ErrorResponse> missingFileExceptionHandler(
            HttpServletRequest request, MissingFileException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
