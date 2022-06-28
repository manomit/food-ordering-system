package com.bsolz.food.ordering.order.service.data.access.order.mapper;

import com.bsolz.food.ordering.domain.valueObject.*;
import com.bsolz.food.ordering.order.service.data.access.order.entity.OrderAddressEntity;
import com.bsolz.food.ordering.order.service.data.access.order.entity.OrderEntity;
import com.bsolz.food.ordering.order.service.data.access.order.entity.OrderItemEntity;
import com.bsolz.food.ordering.order.service.domain.entity.Order;
import com.bsolz.food.ordering.order.service.domain.entity.OrderItem;
import com.bsolz.food.ordering.order.service.domain.entity.Product;
import com.bsolz.food.ordering.order.service.domain.valueObject.OrderItemId;
import com.bsolz.food.ordering.order.service.domain.valueObject.StreetAddress;
import com.bsolz.food.ordering.order.service.domain.valueObject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdeDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getStreetAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemsEntity(order.getItem()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ? String.join(",", order.getFailureMessages()): "")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .streetAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .item(orderItemEntityToOrderItem(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(
                        orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>()
                                : new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(",")))
                )
                .build();
    }

    private List<OrderItem> orderItemEntityToOrderItem(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(
                address.getId(),
                address.getStreet(),
                address.getPostcode(),
                address.getCity()
        );
    }

    private List<OrderItemEntity> orderItemsToOrderItemsEntity(List<OrderItem> item) {
        return item.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress streetAddress) {
        return OrderAddressEntity.builder()
                .id(streetAddress.getId())
                .street(streetAddress.getStreet())
                .postcode(streetAddress.getPostcode())
                .city(streetAddress.getCity())
                .build();
    }
}
