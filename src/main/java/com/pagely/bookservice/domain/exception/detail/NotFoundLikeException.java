package com.pagely.bookservice.domain.exception.detail;

import com.pagely.bookservice.domain.exception.BookErrorCode;
import com.pagely.bookservice.domain.exception.BookException;

public class NotFoundLikeException extends BookException {
    public NotFoundLikeException() {
        super(BookErrorCode.LIKE_NOT_FOUND);
    }
}
