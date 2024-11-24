package com.upinmcSE.coffeeshop.dto.request;

public record CustomerRequest(
        String username,
        String email,
        String password,
        String rePassword
) {
}
