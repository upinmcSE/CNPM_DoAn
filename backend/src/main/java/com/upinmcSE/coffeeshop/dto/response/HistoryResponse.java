package com.upinmcSE.coffeeshop.dto.response;

import com.upinmcSE.coffeeshop.enums.Status;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record HistoryResponse(
        String id,
        List<OrderLineResponse> orderLines,
        double totalPrice,
        Status status,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
}

