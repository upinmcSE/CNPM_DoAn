package com.upinmcSE.coffeeshop.dto.request;

import java.util.List;

public record OrderRequest(
    String customerId,
    List<String> orderLines

) {
}
