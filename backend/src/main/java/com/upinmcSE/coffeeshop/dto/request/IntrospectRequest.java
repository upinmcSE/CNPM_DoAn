package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record IntrospectRequest(
        String token
) {

}
