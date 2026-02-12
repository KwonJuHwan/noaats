package com.saveme.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* --- COMMON (공통) --- */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 형식이 올바르지 않습니다. (YYYY-MM 형식 준수)"),
    /* --- MEMBER (회원) --- */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    /* --- CATEGORY (카테고리) --- */
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    DEFAULT_CATEGORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "기본 카테고리 설정 오류가 발생했습니다. 관리자에게 문의하세요."),

    /* --- LEDGER (가계부/지출) --- */
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "지출 내역을 찾을 수 없습니다."),
    FIXED_COST_NOT_FOUND(HttpStatus.NOT_FOUND, "고정 지출 정보를 찾을 수 없습니다."),
    INVALID_PAYMENT_DAY(HttpStatus.BAD_REQUEST, "결제일은 1~31일 사이여야 합니다."),
    BUDGET_OVERFLOW(HttpStatus.BAD_REQUEST, "예산 설정이 올바르지 않습니다."),
    INCOME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 수입입니다."),

    /* --- Inventory (식재료) --- */
    INVENTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 식재료입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}