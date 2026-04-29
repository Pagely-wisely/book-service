package com.pagely.bookservice.infrastructure.messaging.kafka.producer;

import com.pagely.bookservice.application.port.out.BookSearchEventPort;
import com.pagely.bookservice.domain.event.payload.BookSearchedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaBookSearchEventAdapter implements BookSearchEventPort {
    private static final String BOOK_SEARCH_TOPIC = "book-searched";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishBookSearched(BookSearchedEvent event) {
        publish(BOOK_SEARCH_TOPIC, event.getDomainId(), event);
    }

    private void publish(String topic, String key, Object event) {
        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka 발행 실패 topic: {} key: {}", topic, key, ex);
                    } else {
                        log.info("Kafka 발행 성공 topic: {} key: {} offset: {}",
                                topic, key, result.getRecordMetadata().offset());
                    }
                });
    }
}
