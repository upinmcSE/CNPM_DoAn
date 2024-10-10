package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.*;
import com.upinmcSE.coffeeshop.mapper.OrderMapper;
import com.upinmcSE.coffeeshop.repository.*;
import com.upinmcSE.coffeeshop.service.OrderService;
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


    @Override
    public OrderResponse add(OrderRequest request) {

        Customer customer = customerRepository.findById(request.customerId()).orElseThrow(
                () -> new RuntimeException("Not found customer"));

        List<OrderLine> orderLines = orderLineRepository.findAllById(request.orderLines());

        double total = 0;

        for (OrderLine items : orderLines){
            total += (items.getAmount() * items.getProduct().getPrice());
        }

        Order order = Order.builder()
                .customer(customer)
                .orderLines(orderLines)
                .totalPrice(total)
                .build();
        order = orderRepository.save(order);

        for(OrderLine orderLine: orderLines){
            orderLine.setOrder(order);
            orderLineRepository.saveAndFlush(orderLine);
        }

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllByDay(LocalDate day) {
        return null;
    }
}
