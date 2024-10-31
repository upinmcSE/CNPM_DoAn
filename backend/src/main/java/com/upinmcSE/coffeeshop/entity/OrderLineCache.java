package com.upinmcSE.coffeeshop.entity;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineCache {
    private String id;
    private Product product;
    private int amount;
}
