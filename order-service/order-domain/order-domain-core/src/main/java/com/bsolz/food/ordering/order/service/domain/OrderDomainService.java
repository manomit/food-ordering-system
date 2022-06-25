package com.bsolz.food.ordering.order.service.domain;

import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;
import com.bsolz.food.ordering.order.service.domain.event.OrderCancelledEvent;
import com.bsolz.food.ordering.order.service.domain.event.OrderCreatedEvent;
import com.bsolz.food.ordering.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

   OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approvedOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
