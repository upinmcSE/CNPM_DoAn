package com.upinmcSE.coffeeshop.entity;

import lombok.*;


import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCache {
    private String id;
    private String customerId;
    @ToString.Exclude
    private List<OrderLineCache> orderLineCaches = new ArrayList<>();
    private double totalPrice;
}
