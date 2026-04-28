package com.pagely.bookservice.domain.event.payload;

import com.pagely.bookservice.domain.event.BaseEvent;
import com.pagely.bookservice.domain.model.Book;
import com.pagely.bookservice.domain.model.BookLike;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BookUnlikedEvent extends BaseEvent {
    private static final String DOMAIN_TYPE = "BOOK_LIKE";

    private BookUnlikedEvent(String bookId, Object payload) {
        super(DOMAIN_TYPE, bookId, payload);
    }

    public static BookUnlikedEvent of(BookLike bookLike, Book book) {
        return new BookUnlikedEvent(
                book.getId(),
                new BookUnlikedEvent.Payload(
                        bookLike.getUserId(),
                        book.getId(), book.getTitle(),
                        book.getCategory().getName(),
                        book.getDescription(), book.getAuthors()));
    }

    public record Payload(UUID userId, String bookId, String title, String category, String description,
                          String authors) {
    }
}
