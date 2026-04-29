package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.application.event.BookEventHandler;
import com.pagely.bookservice.application.port.out.BookEventPort;
import com.pagely.bookservice.application.port.out.BookLikeEventPort;
import com.pagely.bookservice.application.port.out.BookSearchEventPort;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookSearchedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class BookEventHandlerAdapter implements BookEventHandler {

    private final BookEventPort bookEventPort;
    private final BookLikeEventPort bookLikeEventPort;
    private final BookSearchEventPort bookSearchEventPort;

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookCreated(BookCreatedEvent event) {
        bookEventPort.publishBookCreated(event);
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookLiked(BookLikedEvent event) {
        bookLikeEventPort.publishBookLiked(event);
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookUnliked(BookUnlikedEvent event) {
        bookLikeEventPort.publishBookUnliked(event);
    }

    @Override
    @EventListener
    public void handleBookSearched(BookSearchedEvent event) {
        bookSearchEventPort.publishBookSearched(event);
    }
}
