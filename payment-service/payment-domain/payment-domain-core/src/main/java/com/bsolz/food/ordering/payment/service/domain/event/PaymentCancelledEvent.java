package com.bsolz.food.ordering.payment.service.domain.event;

import com.bsolz.food.ordering.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCancelled extends PaymentEvent{
    public PaymentCancelled(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }
}
