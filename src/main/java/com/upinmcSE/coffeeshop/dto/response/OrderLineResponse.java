package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record OrderLineResponse(
        String id,
        String productName,
        int amount
) {
}
