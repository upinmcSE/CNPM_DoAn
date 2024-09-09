package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record OrderTypeResponse(
        Integer id,
        String name
) {
}
