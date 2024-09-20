package com.upinmcSE.coffeeshop.dto.request;

public record ProductRequest(
        String name,
        double price,
        String description,
        String category
) {
}
