package com.bsolz.food.ordering.order.service.domain.ports.output.repository;

import com.bsolz.food.ordering.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
