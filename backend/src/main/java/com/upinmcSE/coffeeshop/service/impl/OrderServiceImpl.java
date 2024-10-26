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
import java.util.List;

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
        Order order = new Order();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));

        order.setCustomer(customer);
        order.setTotalPrice(0.0);
        order.setCreatedDate(LocalDate.now());
        order.setModifiedDate(LocalDate.now());

        String orderId = String.valueOf(System.currentTimeMillis());

        cacheService.saveOrderToCache(orderId, order);
        return orderId;

    }

    @Transactional
    @Override // Thêm OrderLine vào Order trong Redis
    public void addOrderLineToOrder(String orderId, OrderLineRequest orderLine) {
        Order order = cacheService.getOrderFromCache(orderId);
        Product product = productRepository.findById(orderLine.productId()).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_PRODUCT));

        if (order != null){
            order.getOrderLines().add(OrderLine.builder()
                            .product(product)
                            .amount(orderLine.amount())
                    .build());
            // Cập nhật tổng số tiền
            double updatedTotal = order.getOrderLines().stream()
                    .map(line -> line.getAmount() * line.getProduct().getPrice())
                    .reduce(0.0, Double::sum);
            order.setTotalPrice(updatedTotal);
            // Lưu lại Order vào Redis
            cacheService.saveOrderToCache(orderId, order);
        } else {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }
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
