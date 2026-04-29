package com.pagely.bookservice.application.dto.result;

import java.util.List;

public record BookSearchListResult(
        List<BookSummaryResult> items,
        long totalResults
) {
    public static BookSearchListResult empty() {
        return new BookSearchListResult(List.of(), 0L);
    }
}
