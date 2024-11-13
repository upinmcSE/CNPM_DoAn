package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record CheckOtpRequest(
        String phone,
        String otp
) {
}
