package com.pagely.bookservice.domain.exception;

import com.pagely.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookErrorCode implements ErrorCode {
    /*
     * =======================================
     * 404 NOT_FOUND
     * =======================================
     */
    BOOK_NOT_FOUND("도서가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    LIKE_NOT_FOUND("좋아요가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    STATS_NOT_FOUND("도서 통계가 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    /*
     * =======================================
     * 400 BAD_REQUEST
     * =======================================
     */
    LIKE_DUPLICATED("중복된 좋아요 요청 입니다.", HttpStatus.BAD_REQUEST),

    /*
     * =======================================
     * 403 FORBIDDEN
     * =======================================
     */
    LIKE_ACCESS_FORBIDDEN("좋아요 기능에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    LIKE_DELETE_FORBIDDEN("좋아요 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ;

    private final String message;
    private final HttpStatus httpStatus;
    private final String code;

    BookErrorCode(String message, HttpStatus httpStatus) {
        this.code = this.name();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
