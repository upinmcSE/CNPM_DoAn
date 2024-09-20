package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.OrderTypeRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderTypeResponse;
import com.upinmcSE.coffeeshop.entity.OrderType;
import com.upinmcSE.coffeeshop.repository.OrderTypeRepository;
import com.upinmcSE.coffeeshop.service.OrderTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderTypeServiceImpl implements OrderTypeService {
    OrderTypeRepository orderTypeRepository;
    @Override
    public OrderTypeResponse add(OrderTypeRequest request) {

        OrderType orderType = OrderType.builder()
                .name(request.name())
                .build();

        orderType = orderTypeRepository.save(orderType);
        return OrderTypeResponse.builder()
                .id(orderType.getId())
                .name(orderType.getName())
                .build();
    }

    @Override
    public OrderTypeResponse update(String id, OrderTypeRequest request) {
        return null;
    }

    @Override
    public List<OrderTypeResponse> getAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
