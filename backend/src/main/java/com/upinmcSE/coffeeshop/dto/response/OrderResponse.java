package com.upinmcSE.coffeeshop.dto.response;

import com.upinmcSE.coffeeshop.entity.OrderLine;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record OrderResponse(
        String id,
        String customerName,
        List<OrderLineResponse> orderLines,
        double totalPrice,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
}
