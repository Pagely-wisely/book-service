package com.pagely.bookservice.infrastructure.client.aladin.exception.detail;

import com.pagely.bookservice.infrastructure.client.aladin.exception.AladinErrorCode;
import com.pagely.common.exception.BusinessException;

public class InvalidFormatAladinException extends BusinessException {
    public InvalidFormatAladinException() {
        super(AladinErrorCode.ALADIN_INVALID_FORMAT);
    }

    public InvalidFormatAladinException(AladinErrorCode errorCode) {
        super(errorCode);
    }
}
