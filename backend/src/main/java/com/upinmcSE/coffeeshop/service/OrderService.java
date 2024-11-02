package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderLine;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    String createEmptyOrder(String customerId);
    void addOrderLineToOrder(String orderId, OrderLineRequest orderLine);
    void removeOrderLineFromOrder(String customerId, String orderId, String orderLineId);
    List<OrderResponse> getAllByDay(LocalDate day);
    List<OrderResponse> getAllByMonth(LocalDate month);
    List<OrderResponse> getAllByYear(LocalDate year);
}
