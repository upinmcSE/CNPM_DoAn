package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.entity.OrderCache;
import com.upinmcSE.coffeeshop.service.CacheService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    @Override // Lưu Order vào Redis
    public void saveOrderToCache(String orderId, OrderCache orderCache) {
        // Không cần khởi tạo orderCache trong phương thức này nữa
        redisTemplate.opsForValue().set("order:" + orderId, orderCache);
        redisTemplate.opsForValue().set("order:byCustomer:" + orderCache.getCustomerId(), orderId);
    }

    @Transactional
    @Override // Lấy Order từ Redis
    public OrderCache getOrderFromCache(String customerId) {
        System.out.println(customerId);
        var orderId = redisTemplate.opsForValue().get("order:byCustomer:" + customerId);
        if (orderId == null) {
            System.out.println("Order not found in Redis.");
            return null;
        }
        var orderCache = redisTemplate.opsForValue().get("order:" + orderId);
        return (OrderCache) orderCache;
    }


    @Transactional
    @Override
    public void removeOrderFromCache(String orderId) {
        redisTemplate.delete("order:" + orderId);
    }
}
