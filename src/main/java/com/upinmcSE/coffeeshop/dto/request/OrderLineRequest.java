package com.upinmcSE.coffeeshop.dto.request;

public record OrderLineRequest(
        String productId,
        int amount
) {
}
