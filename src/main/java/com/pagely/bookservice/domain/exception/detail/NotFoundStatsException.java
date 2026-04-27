package com.pagely.bookservice.domain.exception.detail;

import com.pagely.bookservice.domain.exception.BookErrorCode;
import com.pagely.bookservice.domain.exception.BookException;

public class NotFoundStatsException extends BookException {
    public NotFoundStatsException() {
        super(BookErrorCode.STATS_NOT_FOUND);
    }
}
