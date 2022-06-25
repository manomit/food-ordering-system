package com.bsolz.food.ordering.order.service.domain;

import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.bsolz.food.ordering.order.service.domain.entity.Customer;
import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;
import com.bsolz.food.ordering.order.service.domain.event.OrderCreatedEvent;
import com.bsolz.food.ordering.order.service.domain.exception.OrderDomainException;
import com.bsolz.food.ordering.order.service.domain.mapper.OrderDataMapper;
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
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order =  orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        saveOrder(order);
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant =  orderDataMapper.createdOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

        if(optionalRestaurant.isEmpty()) {
            throw new OrderDomainException("Could not find restaurant with id : " + createOrderCommand.getRestaurantId());

        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if(customer.isEmpty()) {
            throw new OrderDomainException("Could not find customer with customer id: " + customerId);
        }
    }

    private  Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if(orderResult == null) {
            throw new OrderDomainException("Could not save order");
        }
        return orderResult;
    }
}
