package com.pagely.bookservice.domain.exception.detail;

import com.pagely.bookservice.domain.exception.BookErrorCode;
import com.pagely.bookservice.domain.exception.BookException;

public class DuplicatedBookLikeException extends BookException {
    public DuplicatedBookLikeException() {
        super(BookErrorCode.LIKE_DUPLICATED);
    }
}
