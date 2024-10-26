package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.entity.Order;

public interface CacheService {
    void saveOrderToCache(String orderId, Order order);
    Order getOrderFromCache(String orderId);
    void removeOrderFromCache(String orderId);
}
