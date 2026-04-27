package com.pagely.bookservice.infrastructure.client.aladin.exception;

import com.pagely.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AladinErrorCode implements ErrorCode {

    /*
     * =======================================
     * 404 NOT_FOUND
     * =======================================
     */
    ALADIN_ITEM_NOTFOUND("알라딘에 해당 도서 정보가 존재하지않습니다.", HttpStatus.NOT_FOUND),


    /*
     * =======================================
     * 502 BAD_GATEWAY
     * =======================================
     */
    ALADIN_INVALID_FORMAT("알라딘 데이터 파싱에 실패 했습니다.", HttpStatus.BAD_GATEWAY),
    ALADIN_INVALID_DATE_FORMAT("알라딘 날짜 데이터 파싱에 실패했습니다.", HttpStatus.BAD_GATEWAY),
    ;

    private final String message;
    private final HttpStatus httpStatus;
    private final String code;

    AladinErrorCode(String message, HttpStatus httpStatus) {
        this.code = this.name();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
