package com.pagely.bookservice.application.dto.result;

import com.pagely.bookservice.domain.model.BookStats;

public record BookSummaryResult(
        String bookId,
        String title,
        String author,
        String categoryName,
        String publisher,
        String thumbnailUrl,
        int reportCount,
        int likeCount
) {
    // stats 없을 때 (기본값 0)
    public static BookSummaryResult of(
            String bookId, String title, String author,
            String categoryName, String publisher, String thumbnailUrl) {
        return new BookSummaryResult(bookId, title, author, categoryName, publisher, thumbnailUrl, 0, 0);
    }

    // stats merge
    public BookSummaryResult withStats(BookStats stats) {
        return new BookSummaryResult(
                bookId, title, author, categoryName, publisher, thumbnailUrl,
                stats.getReportCount(),
                stats.getLikeCount()
        );
    }
}
