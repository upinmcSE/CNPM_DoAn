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
    public Order createEmptyOrder(String customerId) {
        // Kiem tra xem khach hang nay da co order trong redis chưa
        Order existingOrder = cacheService.getOrderFromCache(customerId);
        if (existingOrder != null) {
            return existingOrder;
        }

        // Create a new empty order
        Order order = new Order();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));

        order.setCustomer(customer);
        order.setTotalPrice(0.0);
        order.setCreatedDate(LocalDate.now());
        order.setModifiedDate(LocalDate.now());

        String orderId = String.valueOf(System.currentTimeMillis());
        order.setId(orderId);

        cacheService.saveOrderToCache(orderId, order);
        return order;
    }


    @Transactional
    @Override // Thêm OrderLine vào Order trong Redis
    public void addOrderLineToOrder(String orderId, OrderLineRequest newOrderLine) {
        Order order = cacheService.getOrderFromCache(orderId);
        Product product = productRepository.findById(newOrderLine.productId()).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_PRODUCT));

        if (order != null) {
            boolean orderLineExists = false;

            for(OrderLine orderLine: order.getOrderLines()){
                if(orderLine.getProduct().getId().equals(newOrderLine.productId())){
                    orderLine.setAmount(orderLine.getAmount() + newOrderLine.amount());
                    orderLineExists = true;
                    break;
                }
            }

            if(!orderLineExists){
                order.getOrderLines().add(OrderLine.builder()
                        .product(product)
                        .amount(newOrderLine.amount())
                        .build());
            }

            // Update the total amount
            double updatedTotal = order.getOrderLines().stream()
                    .map(line -> line.getProduct().getPrice() * line.getAmount())
                    .reduce(0.0, Double::sum);
            order.setTotalPrice(updatedTotal);

            // Save the updated Order back to Redis
            cacheService.saveOrderToCache(orderId, order);
        }else{
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }
    }

    @Transactional
    @Override
    public void decreaseOrderLineQuantity(String orderId, OrderLineRequest orderLineRequest) {
        Order order = cacheService.getOrderFromCache(orderId);
        if(order == null){
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }
        OrderLine orderLine = (OrderLine) order.getOrderLines().stream().filter(
                line -> line.getProduct().getId().equals(orderLineRequest.productId())
        ).findFirst().orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_ORDER));

        orderLine.setAmount(orderLine.getAmount() - orderLineRequest.amount());

        double updatedTotal = order.getOrderLines().stream()
                .map(line -> line.getProduct().getPrice() * line.getAmount())
                .reduce(0.0, Double::sum);
        order.setTotalPrice(updatedTotal);

        // Save the updated Order back to Redis
        cacheService.saveOrderToCache(orderId, order);

    }

    @Transactional
    @Override
    public void increaseOrderLineQuantity(String orderId, OrderLineRequest orderLineRequest) {
        Order order = cacheService.getOrderFromCache(orderId);
        if (order == null) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        OrderLine orderLine = order.getOrderLines().stream()
                .filter(line -> line.getProduct().getId().equals(orderLineRequest.productId()))
                .findFirst()
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_ORDER_LINE));

        orderLine.setAmount(orderLine.getAmount() + orderLineRequest.amount());

        // Update the total amount
        double updatedTotal = order.getOrderLines().stream()
                .map(line -> line.getProduct().getPrice() * line.getAmount())
                .reduce(0.0, Double::sum);
        order.setTotalPrice(updatedTotal);

        // Save the updated Order back to Redis
        cacheService.saveOrderToCache(orderId, order);
    }

    @Override
    public void removeOrderLineFromOrder(String orderId, OrderLineRequest orderLine) {

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
