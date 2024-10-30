package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderLine;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createEmptyOrder(String customerId);
    void addOrderLineToOrder(String orderId, OrderLineRequest orderLine);
    void decreaseOrderLineQuantity(String orderId, OrderLineRequest orderLine);
    void increaseOrderLineQuantity(String orderId, OrderLineRequest orderLine);
    void removeOrderLineFromOrder(String orderId, OrderLineRequest orderLine);
    List<OrderResponse> getAllByDay(LocalDate day);
    List<OrderResponse> getAllByMonth(LocalDate month);
    List<OrderResponse> getAllByYear(LocalDate year);
}
