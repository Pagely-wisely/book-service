package com.pagely.bookservice.domain.exception.detail;

import com.pagely.bookservice.domain.exception.BookErrorCode;
import com.pagely.bookservice.domain.exception.BookException;

public class NoPermissionBookLikeException extends BookException {
    public NoPermissionBookLikeException() {
        super(BookErrorCode.LIKE_ACCESS_FORBIDDEN);
    }

    public NoPermissionBookLikeException(BookErrorCode errorCode) {
        super(errorCode);
    }
}
