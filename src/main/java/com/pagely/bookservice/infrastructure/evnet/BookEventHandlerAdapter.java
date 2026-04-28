package com.pagely.bookservice.infrastructure.evnet;

import com.pagely.bookservice.application.event.BookEventHandler;
import com.pagely.bookservice.application.port.out.BookEventPort;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class BookEventHandlerAdapter implements BookEventHandler {

    private final BookEventPort bookEventPort;

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookCreated(BookCreatedEvent event) {
        bookEventPort.publishBookCreated(event);
    }
}
