package com.pagely.bookservice.domain.event.payload;

import com.pagely.bookservice.domain.event.BaseEvent;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BookSearchedEvent extends BaseEvent {
    private static final String DOMAIN_TYPE = "BOOK_SEARCH";

    private BookSearchedEvent(Object payload) {
        super(DOMAIN_TYPE, payload);
    }

    private BookSearchedEvent(String bookId, Object payload) {
        super(DOMAIN_TYPE, bookId, payload);
    }

    public static BookSearchedEvent of(UUID userId, String keyword) {
        return new BookSearchedEvent(
                new BookSearchedEvent.Payload(
                        userId, keyword,
                        null, null, null, null
                ));
    }

    public static BookSearchedEvent ofDetail(UUID userId, String bookId, String title, String category,
                                             String authors) {
        return new BookSearchedEvent(
                bookId,
                new BookSearchedEvent.Payload(
                        userId, title,
                        bookId, title, authors, category
                ));
    }

    public record Payload(UUID userId, String keyword, String bookId, String bookName, String authors,
                          String category) {
    }

}
