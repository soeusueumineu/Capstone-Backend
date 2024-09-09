package com.capstone.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    AUTHENTICATION_USER(HttpStatus.UNAUTHORIZED, "로그인하지 않은 사용자입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다"),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    USER_ALREADY_JOIN(HttpStatus.UNAUTHORIZED, "이미 가입된 회원입니다"),

    FAILED_WRITE_REVIEW(HttpStatus.UNAUTHORIZED, "리뷰 작성에 실패했습니다"),
    ALREADY_IN_CART(HttpStatus.BAD_REQUEST, "이미 찜한 상품입니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
