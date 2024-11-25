package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record CustomerUpdateRequest(
        String fullName,
        boolean gender,
        int age,
        String email
) {
}
