package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderCache;

public interface CacheService {
    void saveOrderToCache(String orderId, OrderCache order);
    OrderCache getOrderFromCache(String orderId);
    void removeOrderFromCache(String orderId);
}
