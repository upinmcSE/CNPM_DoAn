package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record PaymentResponse(
        String paymentUrl
) {
}