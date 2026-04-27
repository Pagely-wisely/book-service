package com.pagely.bookservice.infrastructure.client.aladin.exception;

import com.pagely.common.exception.BusinessException;
import com.pagely.common.exception.ErrorCode;

class AladinException extends BusinessException {
    public AladinException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AladinException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

    public AladinException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
