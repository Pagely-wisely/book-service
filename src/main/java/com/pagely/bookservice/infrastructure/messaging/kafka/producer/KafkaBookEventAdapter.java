package com.pagely.bookservice.infrastructure.messaging.kafka.producer;

import com.pagely.bookservice.application.port.out.BookEventPort;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaBookEventAdapter implements BookEventPort {
    private static final String BOOK_CREATED_TOPIC = "book-created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishBookCreated(BookCreatedEvent event) {
        kafkaTemplate.send(BOOK_CREATED_TOPIC,
                event.getDomainId(),
                event);
    }
}
