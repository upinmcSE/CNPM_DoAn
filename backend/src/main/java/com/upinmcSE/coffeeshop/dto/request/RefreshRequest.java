package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record RefreshRequest(
        String token
) {
}
