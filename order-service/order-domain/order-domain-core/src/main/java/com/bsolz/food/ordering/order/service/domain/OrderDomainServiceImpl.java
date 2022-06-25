package com.bsolz.food.ordering.order.service.domain;

import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.Product;
import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;
import com.bsolz.food.ordering.order.service.domain.event.OrderCancelledEvent;
import com.bsolz.food.ordering.order.service.domain.event.OrderCreatedEvent;
import com.bsolz.food.ordering.order.service.domain.event.OrderPaidEvent;
import com.bsolz.food.ordering.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }




    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id : {} is paid ", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void approvedOrder(Order order) {
        order.approved();
        log.info("Order with id : {} is approved ", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cncelled", order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() + "is currently not active");
        }
    }
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItem()
                .forEach(orderItem -> {
                    restaurant.getProducts().forEach(restaurantProduct -> {
                        Product currentProduct = orderItem.getProduct();
                        if(currentProduct.equals(restaurantProduct)) {
                            currentProduct.updateWithConfirmationNameAndPrice(
                                    restaurantProduct.getName(), restaurantProduct.getPrice()
                            );
                        }
                    });
                });
    }
}
