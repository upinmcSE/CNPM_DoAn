package com.upinmcSE.coffeeshop.dto.request;

import java.util.List;

public record OrderRequest(
    String orderType,
    String customerId,
    String employeeId,
    List<String> orderLines

) {
}
