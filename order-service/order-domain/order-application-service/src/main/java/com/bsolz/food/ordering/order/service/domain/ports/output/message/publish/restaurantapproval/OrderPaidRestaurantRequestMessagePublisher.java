package com.bsolz.food.ordering.order.service.domain.ports.output.message.publish.restaurantapproval;

import com.bsolz.food.ordering.domain.event.publisher.DomainEventPublisher;
import com.bsolz.food.ordering.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
