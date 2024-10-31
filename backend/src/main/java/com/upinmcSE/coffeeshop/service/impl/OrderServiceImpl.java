package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.*;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.OrderMapper;
import com.upinmcSE.coffeeshop.repository.*;
import com.upinmcSE.coffeeshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderLineRepository orderLineRepository;
    OrderMapper orderMapper;
    CustomerRepository customerRepository;
    CacheServiceImpl cacheService;
    ProductRepository productRepository;

    @Transactional
    @Override
    public String createEmptyOrder(String customerId) {
        // Kiem tra xem khach hang nay da co order trong redis chưa
        OrderCache existingOrderCache = cacheService.getOrderFromCache(customerId);
        if (existingOrderCache != null) {
            return existingOrderCache.getId();
        }

        // Tạo một đơn hàng rỗng
        OrderCache orderCache = new OrderCache();
        String orderId = UUID.randomUUID().toString();
        orderCache.setId(orderId);
        orderCache.setCustomerId(customerId);
        orderCache.setOrderLineCaches(new ArrayList<>());
        orderCache.setTotalPrice(0.0);

        // Lưu vào Redis dưới dạng OrderCache
        cacheService.saveOrderToCache(orderId, orderCache);
        return orderId;
    }


    @Transactional
    @Override // Thêm OrderLine vào Order trong Redis
    public void addOrderLineToOrder(String customerId, OrderLineRequest newOrderLine) {
        System.out.println("Adding order line...");

        // Retrieve OrderCache from Redis
        OrderCache orderCache = cacheService.getOrderFromCache(customerId);
        System.out.println("OrderCache xxx : "+orderCache);
        if (orderCache == null) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        // Check if product exists
        Product product = productRepository.findById(newOrderLine.productId())
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_PRODUCT));
        System.out.println("Product xx: "+product.getId());

        // Check for existing order line and update quantity if it exists
        boolean orderLineExists = false;

        for (OrderLineCache orderLine : orderCache.getOrderLineCaches()) {
            if (orderLine.getProduct().getId().equals(newOrderLine.productId())) {
                orderLine.setAmount(orderLine.getAmount() + newOrderLine.amount());
                orderLineExists = true;
                break;
            }
        }

        // If order line does not exist, add a new one with product price
        if (!orderLineExists) {
            OrderLineCache newLine = OrderLineCache.builder()
                    .id(UUID.randomUUID().toString())
                    .product(product)
                    .amount(newOrderLine.amount())
                    .build();
            orderCache.getOrderLineCaches().add(newLine);
        }

        System.out.println("OrderLine xx: "+orderCache);
        // Update total price using cached price in OrderLineCaches
        double updatedTotalPrice = orderCache.getOrderLineCaches().stream()
                .mapToDouble(line -> line.getProduct().getPrice() * line.getAmount())
                .sum();
        orderCache.setTotalPrice(updatedTotalPrice);
        System.out.println("Total price: "+updatedTotalPrice);

        // Save updated order in Redis
        cacheService.saveOrderToCache(orderCache.getId(), orderCache);
    }


    @Override
    public void removeOrderLineFromOrder(String orderId, OrderLineRequest orderLine) {
        OrderCache orderCache = cacheService.getOrderFromCache(orderId);
        if(orderCache == null){
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        OrderLineCache orderLineToRemove = orderCache.getOrderLineCaches().stream()
                .filter(line -> line.getProduct().getId().equals(orderLine.productId()))
                .findFirst()
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_ORDER_LINE));

        orderCache.getOrderLineCaches().remove(orderLineToRemove);

        double updatedTotal = orderCache.getOrderLineCaches().stream()
                .map(line -> line.getProduct().getPrice() * line.getAmount())
                .reduce(0.0, Double::sum);
        orderCache.setTotalPrice(updatedTotal);


        // Save the updated Order back to Redis
        cacheService.saveOrderToCache(orderId, orderCache);
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByDay(LocalDate day) {
        return List.of();
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByMonth(LocalDate month) {
        return List.of();
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByYear(LocalDate year) {
        return List.of();
    }

}
