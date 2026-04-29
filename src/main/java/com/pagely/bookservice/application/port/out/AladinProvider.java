package com.pagely.bookservice.application.port.out;

import com.pagely.bookservice.application.dto.result.BookResult;
import com.pagely.bookservice.application.dto.result.BookSearchListResult;

public interface AladinProvider {
    BookResult getItem(String bookId);

    BookSearchListResult searchItems(String query, String queryType, int size, int page);
}
