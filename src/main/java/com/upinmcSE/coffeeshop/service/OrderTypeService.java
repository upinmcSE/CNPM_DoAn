package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.OrderTypeRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderTypeResponse;

import java.util.List;

public interface OrderTypeService {
    OrderTypeResponse add(OrderTypeRequest request);
    OrderTypeResponse update(String id, OrderTypeRequest request);
    List<OrderTypeResponse> getAll();
    void delete(String id);
}
