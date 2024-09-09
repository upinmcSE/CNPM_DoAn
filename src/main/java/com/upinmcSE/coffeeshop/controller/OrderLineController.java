package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderLineResponse;
import com.upinmcSE.coffeeshop.service.impl.OrderLineServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order-lines")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderLineController {
    OrderLineServiceImpl orderLineService;

    @PostMapping("/add")
    public ResponseEntity<OrderLineResponse> add(@RequestBody OrderLineRequest request){
        return ResponseEntity.ok().body(orderLineService.add(request));
    }
}
