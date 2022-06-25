package com.bsolz.food.ordering.order.service.domain;

import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderResponse;
import com.bsolz.food.ordering.order.service.domain.entity.Customer;
import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;
import com.bsolz.food.ordering.order.service.domain.event.OrderCreatedEvent;
import com.bsolz.food.ordering.order.service.domain.exception.OrderDomainException;
import com.bsolz.food.ordering.order.service.domain.mapper.OrderDataMapper;
import com.bsolz.food.ordering.order.service.domain.ports.output.message.publish.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.bsolz.food.ordering.order.service.domain.ports.output.repository.CustomerRepository;
import com.bsolz.food.ordering.order.service.domain.ports.output.repository.OrderRepository;
import com.bsolz.food.ordering.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;


    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {

        OrderCreatedEvent orderCreatedEvent =  orderCreateHelper.persistOrder(createOrderCommand);
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "");
    }


}
