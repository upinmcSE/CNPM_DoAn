package com.upinmcSE.coffeeshop.dto.response;

import java.util.List;

public record RecommendationResponse(
        String customerId,
        List<String> recommendations
) {
}
