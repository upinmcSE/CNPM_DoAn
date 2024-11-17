package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record InventoryResponse(
        Long id,
        String productId,
        String productName,
        Integer quantity
) {
}
