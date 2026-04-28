package com.pagely.bookservice.application.port.out;

import com.pagely.bookservice.application.dto.result.BookResult;

public interface AladinProvider {
    BookResult getItem(String bookId);
}
