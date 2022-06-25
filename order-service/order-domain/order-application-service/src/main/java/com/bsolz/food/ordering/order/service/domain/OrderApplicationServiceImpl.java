package com.bsolz.food.ordering.order.service.domain;

import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.bsolz.food.ordering.order.service.domain.dto.create.CreateOrderResponse;
import com.bsolz.food.ordering.order.service.domain.dto.track.TrackOrderQuery;
import com.bsolz.food.ordering.order.service.domain.dto.track.TrackOrderResponse;
import com.bsolz.food.ordering.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final OrderTrackHandlerCommand orderTrackHandlerCommand;

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return null;
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
