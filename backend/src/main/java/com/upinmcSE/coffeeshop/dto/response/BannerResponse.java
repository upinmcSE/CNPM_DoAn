package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

@Builder
public record BannerResponse(
        Integer id,
        String urlImage
) {
}
