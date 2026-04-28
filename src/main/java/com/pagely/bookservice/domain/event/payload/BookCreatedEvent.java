package com.pagely.bookservice.domain.event.payload;

import com.pagely.bookservice.domain.event.BaseEvent;
import com.pagely.bookservice.domain.model.Book;
import lombok.Getter;

@Getter
public class BookCreatedEvent extends BaseEvent {
    private static final String DOMAIN_TYPE = "BOOK";

    private BookCreatedEvent(String bookId, Object payload) {
        super(DOMAIN_TYPE, bookId, payload);
    }

    public static BookCreatedEvent of(Book book) {
        return new BookCreatedEvent(
                book.getId(),
                new Payload(book.getId(), book.getTitle(),
                        book.getCategory().getName(),
                        book.getDescription(), book.getAuthors()));
    }

    public record Payload(String bookId, String title, String category, String description, String authors) {
    }
}
