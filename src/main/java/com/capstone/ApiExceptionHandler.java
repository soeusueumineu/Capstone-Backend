package com.capstone;

import com.capstone.controller.*;
import com.capstone.exception.ErrorCode;
import com.capstone.exception.ErrorException;
import com.capstone.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {
        MemberController.class, CartController.class, ReviewController.class, ItemDetailController.class, MyPageController.class, RecommendController.class, FileController.class})
public class ApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchException(ErrorException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }
}
