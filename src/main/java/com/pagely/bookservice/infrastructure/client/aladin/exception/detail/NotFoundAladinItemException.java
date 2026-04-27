package com.pagely.bookservice.infrastructure.client.aladin.exception.detail;

import com.pagely.bookservice.infrastructure.client.aladin.exception.AladinErrorCode;
import com.pagely.common.exception.BusinessException;

public class NotFoundAladinItemException extends BusinessException {
    public NotFoundAladinItemException() {
        super(AladinErrorCode.ALADIN_ITEM_NOTFOUND);
    }
}
