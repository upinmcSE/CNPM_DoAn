package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record PaymentOrderResponse(
        String id,
        String customerId,
        String orderId,
        String paymentMethod,
        String status,
        double totalPrice
) {
}
