package com.bsolz.food.ordering.order.service.domain.event;

import com.bsolz.food.ordering.domain.event.DomainEvent;
import com.bsolz.food.ordering.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent  extends  OrderEvent{
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
