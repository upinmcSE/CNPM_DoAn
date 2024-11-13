package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record ResetPasswordRequest(
        String phone,
        String password
) {
}
