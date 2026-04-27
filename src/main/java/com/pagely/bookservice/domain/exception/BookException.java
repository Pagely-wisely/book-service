package com.pagely.bookservice.domain.exception;

import com.pagely.common.exception.BusinessException;
import com.pagely.common.exception.ErrorCode;

public class BookException extends BusinessException {
    public BookException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BookException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

    public BookException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
