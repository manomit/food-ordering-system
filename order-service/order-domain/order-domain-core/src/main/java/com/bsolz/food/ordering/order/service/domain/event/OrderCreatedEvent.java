package com.bsolz.food.ordering.order.service.domain.event;

import com.bsolz.food.ordering.domain.event.DomainEvent;
import com.bsolz.food.ordering.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent{

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
