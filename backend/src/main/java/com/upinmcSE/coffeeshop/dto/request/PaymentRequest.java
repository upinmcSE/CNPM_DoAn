package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record PaymentRequest(
        String orderId,
        String bankCode
) {
}
