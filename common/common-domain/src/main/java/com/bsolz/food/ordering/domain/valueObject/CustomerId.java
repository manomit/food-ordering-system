package com.bsolz.food.ordering.domain.valueObject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID> {

    public CustomerId(UUID value) {
        super(value);
    }
}
