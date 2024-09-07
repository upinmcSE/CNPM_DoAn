package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record PermissionResponse(
        String id,
        String name,
        String description
) {
}
