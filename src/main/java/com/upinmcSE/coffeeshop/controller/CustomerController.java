package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.service.impl.CustomerServiceImpl;
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
@RequestMapping("/api/v1/customers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerServiceImpl customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> add(@RequestBody CustomerRequest request){
        return ResponseEntity.ok().body(customerService.add(request));
    }
}
