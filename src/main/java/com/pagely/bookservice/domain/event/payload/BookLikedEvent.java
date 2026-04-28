package com.pagely.bookservice.domain.event.payload;

import com.pagely.bookservice.domain.event.BaseEvent;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.model.BookLike;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BookLikedEvent extends BaseEvent {
    private static final String DOMAIN_TYPE = "BOOK_LIKE";

    private BookLikedEvent(String bookId, Object payload) {
        super(DOMAIN_TYPE, bookId, payload);
    }

    public static BookLikedEvent of(BookLike bookLike, Book book) {
        return new BookLikedEvent(
                book.getId(),
                new BookLikedEvent.Payload(
                        bookLike.getUserId(),
                        book.getId(), book.getTitle(),
                        book.getCategory().getName(),
                        book.getDescription(), book.getAuthors()));
    }

    public record Payload(UUID userId, String bookId, String title, String category, String description,
                          String authors) {
    }
}
