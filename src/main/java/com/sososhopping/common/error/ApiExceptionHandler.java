package com.sososhopping.common.error;

import com.sososhopping.auth.exception.DuplicateMemberException;
import com.sososhopping.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlException(SQLException e) {
        log.error("[SQLException]", e);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("database error"));
    }

    @ExceptionHandler({DuplicateMemberException.class})
    public ResponseEntity<ErrorResponse> duplicateMemberException(DuplicateMemberException e) {
        log.error("[DuplicateMemberException]", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Api400Exception.class)
    public ResponseEntity api400Exception(final Api400Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

//    @ExceptionHandler(Api401Exception.class)
//    public ResponseEntity api401Exception(HttpServletRequest request, final Api401Exception e) {
//        e.printStackTrace();
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(new ErrorResponse(e.getMessage()));
//    }
//
//    @ExceptionHandler(Api403Exception.class)
//    public ResponseEntity api403Exception(HttpServletRequest request, final Api403Exception e) {
//        e.printStackTrace();
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(new ErrorResponse(e.getMessage()));
//    }
//
//    @ExceptionHandler(Api404Exception.class)
//    public ResponseEntity api404Exception(HttpServletRequest request, final Api404Exception e) {
//        e.printStackTrace();
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse(e.getMessage()));
//    }
//
//    @ExceptionHandler(Api409Exception.class)
//    public ResponseEntity api409Exception(HttpServletRequest request, final Api409Exception e) {
//        e.printStackTrace();
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(new ErrorResponse(e.getMessage()));
//    }
//
//    @ExceptionHandler(Api500Exception.class)
//    public ResponseEntity api500Exception(HttpServletRequest request, final Api500Exception e) {
//        e.printStackTrace();
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ErrorResponse(e.getMessage()));
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity constraintViolationException(HttpServletRequest request, final ConstraintViolationException e) {
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(new ErrorResponse(e.getMessage()));
//    }
}
