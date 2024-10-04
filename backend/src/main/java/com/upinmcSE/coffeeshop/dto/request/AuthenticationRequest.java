package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;


@Builder
public record AuthenticationRequest(
        String username,
        String password
) {}
