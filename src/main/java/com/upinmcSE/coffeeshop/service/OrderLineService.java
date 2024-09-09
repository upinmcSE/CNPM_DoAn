package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderLineResponse;

public interface OrderLineService {
    OrderLineResponse add(OrderLineRequest request);
    OrderLineResponse update(String id, OrderLineRequest request);

    void delete(String id);
}
