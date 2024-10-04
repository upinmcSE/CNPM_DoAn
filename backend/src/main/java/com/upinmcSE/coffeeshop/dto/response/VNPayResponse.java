package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record VNPayResponse(
        String code,
        String message,
        String paymentUrl
) {
}
