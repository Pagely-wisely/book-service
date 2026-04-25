package com.pagely.bookservice.application.dto.result;

import com.pagely.bookservice.domain.model.Book;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResult {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String thumbnailUrl;
    private String description;
    private LocalDateTime publishedAt;
    private Long categoryId;
    private String categoryName;

    public static BookResult from(Book book) {
        return BookResult.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthors())
                .publisher(book.getPublisher())
                .thumbnailUrl(book.getThumbnailUrl())
                .description(book.getDescription())
                .publishedAt(book.getPublishedAt())
                .categoryId(book.getCategory().getId())
                .categoryName(book.getCategory().getName())
                .build();
    }
}
