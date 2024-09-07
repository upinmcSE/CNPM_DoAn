package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record ProductResponse(
        String id,
        String name,
        double price,
        String description,
        String category
) {
}
