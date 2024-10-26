package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.service.CacheService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override // Lưu Order vào Redis
    public void saveOrderToCache(String orderId, Order order) {
        redisTemplate.opsForValue().set("order:" + orderId, order);
    }

    @Transactional
    @Override // Lấy Order từ Redis
    public Order getOrderFromCache(String orderId) {
        return (Order) redisTemplate.opsForValue().get("order:" + orderId);
    }

    @Transactional
    @Override
    public void removeOrderFromCache(String orderId) {
        redisTemplate.delete("order:" + orderId);
    }
}
