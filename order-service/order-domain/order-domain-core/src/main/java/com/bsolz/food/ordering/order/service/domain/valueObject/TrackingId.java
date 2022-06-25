package com.bsolz.food.ordering.order.service.domain.valueObject;

import com.bsolz.food.ordering.domain.valueObject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {

    public TrackingId(UUID value) {
        super(value);
    }
}
