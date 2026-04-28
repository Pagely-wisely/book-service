package com.pagely.bookservice.infrastructure.messaging.kafka.producer;

import com.pagely.bookservice.application.port.out.BookEventPort;
import com.pagely.bookservice.domain.event.payload.BookCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaBookEventAdapter implements BookEventPort {
    private static final String BOOK_CREATED_TOPIC = "book-created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishBookCreated(BookCreatedEvent event) {
        publish(BOOK_CREATED_TOPIC, event.getDomainId(), event);
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
