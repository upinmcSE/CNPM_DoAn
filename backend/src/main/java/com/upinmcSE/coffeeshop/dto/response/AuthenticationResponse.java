package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token,
        boolean authenticated
) {
}
