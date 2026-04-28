package com.pagely.bookservice.infrastructure.messaging.kafka.producer;

import com.pagely.bookservice.application.port.out.BookLikeEventPort;
import com.pagely.bookservice.domain.event.payload.BookLikedEvent;
import com.pagely.bookservice.domain.event.payload.BookUnlikedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaBookLikeEventAdapter implements BookLikeEventPort {
    private static final String BOOK_LIKED_TOPIC = "book-liked";
    private static final String BOOK_UNLIKED_TOPIC = "book-unliked";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishBookLiked(BookLikedEvent event) {
        kafkaTemplate.send(BOOK_LIKED_TOPIC,
                event.getDomainId(),
                event);
    }

    @Override
    public void publishBookUnliked(BookUnlikedEvent event) {
        kafkaTemplate.send(BOOK_UNLIKED_TOPIC,
                event.getDomainId(),
                event);
    }
}
