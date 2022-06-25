package com.bsolz.food.ordering.order.service.domain.ports.output.message.publish.payment;

import com.bsolz.food.ordering.domain.event.publisher.DomainEventPublisher;
import com.bsolz.food.ordering.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
