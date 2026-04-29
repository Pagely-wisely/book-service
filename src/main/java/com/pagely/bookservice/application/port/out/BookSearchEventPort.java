package com.pagely.bookservice.application.port.out;

import com.pagely.bookservice.domain.event.payload.BookSearchedEvent;

public interface BookSearchEventPort {
    void publishBookSearched(BookSearchedEvent event);
}
