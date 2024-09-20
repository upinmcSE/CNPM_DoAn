package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.OrderTypeRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderTypeResponse;
import com.upinmcSE.coffeeshop.service.impl.OrderTypeServiceImpl;
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
@RequestMapping("/api/v1/order-type")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderTypeController {
    OrderTypeServiceImpl orderTypeService;

    @PostMapping("/add")
    public ResponseEntity<OrderTypeResponse> add(@RequestBody OrderTypeRequest request){
        return ResponseEntity.ok().body(orderTypeService.add(request));
    }

}
