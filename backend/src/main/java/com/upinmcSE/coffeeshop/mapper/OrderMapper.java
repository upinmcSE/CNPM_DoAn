package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.response.OrderLineResponse;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderCache;
import com.upinmcSE.coffeeshop.entity.OrderLine;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Autowired
    private CustomerRepository customerRepository;
    public OrderResponse toOrderResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomer().getFullName())
                .orderLines(order.getOrderLines().stream()
                        .map(this::toOrderLineResponse)
                        .collect(Collectors.toList()))
                .totalPrice(order.getTotalPrice())
                .createdDate(order.getCreatedDate())
                .modifiedDate(order.getModifiedDate())
                .build();
    }

    public Order toOrder(OrderCache orderCache){
        Customer customer = customerRepository.findById(orderCache.getCustomerId()).orElseThrow();
        return Order.builder()
                .id(orderCache.getId())
                .customer(customer)
                .orderLines(orderCache.getOrderLineCaches().stream()
                        .map(orderLineCache -> OrderLine.builder()
                                .id(orderLineCache.getId())
                                .product(orderLineCache.getProduct())
                                .amount(orderLineCache.getAmount())
                                .build())
                        .collect(Collectors.toList()))
                .totalPrice(orderCache.getTotalPrice())
                .build();

    }
    private OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .productName(orderLine.getProduct().getName())
                .amount(orderLine.getAmount())
                .build();
    }
}
