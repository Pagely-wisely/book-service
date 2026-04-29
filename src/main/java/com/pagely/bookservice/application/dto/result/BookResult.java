package com.pagely.bookservice.application.dto.result;

import com.pagely.bookservice.domain.model.Book;
import java.time.LocalDateTime;

public record BookResult(
        String id,
        String title,
        String author,
        String publisher,
        String thumbnailUrl,
        String description,
        LocalDateTime publishedAt,
        Long categoryId,
        String categoryName
) {
    public static BookResult from(Book book) {
        return new BookResult(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublisher(),
                book.getThumbnailUrl(),
                book.getDescription(),
                book.getPublishedAt(),
                book.getCategory().getId(),
                book.getCategory().getName()
        );
    }
}
