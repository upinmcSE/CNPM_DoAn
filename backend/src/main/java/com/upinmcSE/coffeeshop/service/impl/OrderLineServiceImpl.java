package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderLineResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.OrderLine;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.OrderLineRepository;
import com.upinmcSE.coffeeshop.repository.ProductRepository;
import com.upinmcSE.coffeeshop.service.OrderLineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderLineServiceImpl implements OrderLineService {
    OrderLineRepository orderLineRepository;
    ProductRepository productRepository;
    @Override
    public OrderLineResponse add(OrderLineRequest request) {
        OrderLine orderLine = OrderLine.builder()
                .product(productRepository.findById(request.productId()).orElseThrow(
                        () -> new ErrorException(ErrorCode.NOT_FOUND_PRODUCT)
                ))
                .amount(request.amount())
                .build();
        orderLine = orderLineRepository.save(orderLine);
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .productName(orderLine.getProduct().getName())
                .amount(orderLine.getAmount())
                .build();
    }

    @Override
    public OrderLineResponse update(String id, OrderLineRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
