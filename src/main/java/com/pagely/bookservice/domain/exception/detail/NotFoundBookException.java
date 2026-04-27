package com.pagely.bookservice.domain.exception.detail;

import com.pagely.bookservice.domain.exception.BookErrorCode;
import com.pagely.bookservice.domain.exception.BookException;

public class NotFoundBookException extends BookException {
    public NotFoundBookException() {
        super(BookErrorCode.BOOK_NOT_FOUND);
    }
}
