package com.upinmcSE.coffeeshop.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderOrderlineResponse {
    private String orderId;
    private String customerId;
    private double totalPrice;
    private String orderLineId;
    private String productId;
    private int amount;


}
