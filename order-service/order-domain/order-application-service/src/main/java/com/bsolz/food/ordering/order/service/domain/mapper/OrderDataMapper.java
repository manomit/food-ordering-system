package com.bsolz.food.ordering.order.service.domain.mapper;

import com.bsolz.food.ordering.domain.valueObject.CustomerId;
import com.bsolz.food.ordering.domain.valueObject.Money;
import com.bsolz.food.ordering.domain.valueObject.ProductId;
import com.bsolz.food.ordering.domain.valueObject.RestaurantId;
import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderResponse;
import com.bsolz.food.ordering.order.service.domain.dto.create.ItemOrder;
import com.bsolz.food.ordering.order.service.domain.dto.create.OrderAddress;
import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.OrderItem;
import com.bsolz.food.ordering.order.service.domain.entity.Product;
import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;
import com.bsolz.food.ordering.order.service.domain.valueObject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createdOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(
                        itemOrder -> new Product(new ProductId(itemOrder.getProductId()))).collect(Collectors.toList())
                )
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .streetAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .item(orderItemsToOrderItemsEntity(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntity(List<ItemOrder> items) {
        return items.stream()
                .map(itemOrder ->
                        OrderItem.builder()
                                .product(new Product(new ProductId(itemOrder.getProductId())))
                                .price(new Money(itemOrder.getPrice()))
                                .quantity(itemOrder.getQuantity())
                                .subTotal(new Money(itemOrder.getSubTotals()))
                                .build()
                        ).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getPostcode(),
                address.getCity()
        );
    }
}
