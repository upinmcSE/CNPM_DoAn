package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record PaymentInfo(
        String phoneNumber,
        String address
) {
}
