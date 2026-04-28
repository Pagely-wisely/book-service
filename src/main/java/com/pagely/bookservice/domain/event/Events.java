package com.pagely.bookservice.domain.event;

import com.pagely.common.exception.BusinessException;
import com.pagely.common.exception.CommonErrorCode;

public class Events {

    private static DomainEventPublisher publisher;

    public static void trigger(BaseEvent event) {
        if (publisher == null) {
            throw new BusinessException(CommonErrorCode.EVENT_PUBLISH_FAILURE);
        }
        publisher.publish(event);
    }

    public static void initialize(DomainEventPublisher eventPublisher) {
        Events.publisher = eventPublisher;
    }
}
