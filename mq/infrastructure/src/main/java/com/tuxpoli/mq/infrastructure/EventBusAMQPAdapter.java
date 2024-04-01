package com.tuxpoli.mq.infrastructure;

import com.tuxpoli.common.domain.EventBus;
import org.springframework.amqp.core.AmqpTemplate;

public class EventBusAMQPAdapter implements EventBus {

    private final AmqpTemplate amqpTemplate;

    public EventBusAMQPAdapter(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publish(Object message, String to) {
        amqpTemplate.convertAndSend(
                "internal.exchange",
                "internal.%s.routing-key".formatted(to),
                message
        );
    }
}
