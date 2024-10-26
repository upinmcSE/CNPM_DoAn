package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record OrderLineRequest(
        String productId,
        int amount
) {
}
