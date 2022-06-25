package com.bsolz.food.ordering.order.service.domain.valueObject;

import com.bsolz.food.ordering.domain.valueObject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
