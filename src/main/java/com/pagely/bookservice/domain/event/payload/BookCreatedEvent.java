package com.pagely.bookservice.domain.event.payload;

import com.pagely.bookservice.domain.event.BaseEvent;
import com.pagely.bookservice.domain.model.Book;
import lombok.Getter;

@Getter
public class BookCreatedEvent extends BaseEvent {
    private static final String DOMAIN_TYPE = "BOOK";

    private final String title;
    private final String authors;
    private final String category;
    private final String description;

    private BookCreatedEvent(String isbn, String title,
                             String authors, String category, String description) {
        super(DOMAIN_TYPE, isbn);
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.description = description;
    }

    public static BookCreatedEvent of(Book book) {
        return new BookCreatedEvent(
                book.getId(),
                book.getTitle(),
                book.getAuthors(),
                book.getCategory().getName(),
                book.getDescription()
        );
    }
}
