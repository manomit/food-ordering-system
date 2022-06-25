package com.bsolz.food.ordering.order.service.domain.ports.input.message.listener.restaurantApproval;

import com.bsolz.food.ordering.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderReject(RestaurantApprovalResponse restaurantApprovalResponse);
}
