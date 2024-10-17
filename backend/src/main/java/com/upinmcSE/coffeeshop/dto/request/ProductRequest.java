package com.upinmcSE.coffeeshop.dto.request;

public record ProductRequest(
        String name,
        Double  price,
        String description,
        String category
) {
}
