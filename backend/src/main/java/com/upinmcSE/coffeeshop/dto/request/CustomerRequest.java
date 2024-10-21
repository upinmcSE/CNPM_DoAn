package com.upinmcSE.coffeeshop.dto.request;

public record CustomerRequest(
        String username,
        String password,
        String rePassword
) {
}
