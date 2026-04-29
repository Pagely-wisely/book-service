package com.pagely.bookservice.application.dto.result;

import com.pagely.bookservice.domain.model.BookStats;
import java.time.LocalDateTime;

public record BookDetailResult(
        String id,
        String title,
        String author,
        String publisher,
        String thumbnailUrl,
        String description,
        LocalDateTime publishedAt,
        Long categoryId,
        String categoryName,
        int likeCount,
        int reportCount
) {
    public static BookDetailResult from(BookResult book) {
        return new BookDetailResult(
                book.id(),
                book.title(),
                book.author(),
                book.publisher(),
                book.thumbnailUrl(),
                book.description(),
                book.publishedAt(),
                book.categoryId(),
                book.categoryName(),
                0,
                0
        );
    }

    public BookDetailResult withStats(BookStats stats) {
        return new BookDetailResult(
                id, title, author, publisher, thumbnailUrl,
                description, publishedAt, categoryId, categoryName,
                stats.getLikeCount(),
                stats.getReportCount()
        );
    }
}
