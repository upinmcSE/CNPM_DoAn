package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.entity.OrderCache;

public interface CacheService {
    void saveOrderToCache(String orderId, OrderCache order);
    OrderCache getOrderFromCache(String orderId);
    void removeOrderFromCache(String orderId);

    void saveOtpToCache(String email, String otp);
    String getOtpFromCache(String email);
    void removeOtpFromCache(String email);
}
