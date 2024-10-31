package com.upinmcSE.coffeeshop.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record OrderLineRequest(
        String productId,
        int amount
) {
}
