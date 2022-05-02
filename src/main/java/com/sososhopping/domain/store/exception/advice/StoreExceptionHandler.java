package com.sososhopping.domain.store.exception.advice;

import com.sososhopping.a.dto.ErrorResponse;
import com.sososhopping.domain.store.exception.DuplicateBusinessNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler({DuplicateBusinessNumberException.class})
    public ResponseEntity<ErrorResponse> duplicateBusinessNumberException(
            HttpServletRequest request, DuplicateBusinessNumberException e) {

        log.error("[{}] [{}]", request.getRequestURI(), e.getClass(), e);

        return ResponseEntity
                .status(CONFLICT)
                .body(new ErrorResponse(e.getMessage()));
    }
}
