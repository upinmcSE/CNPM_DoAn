package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.service.impl.CustomerServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getall")
    public ResponseEntity<PageResponse<CustomerResponse>> getProducts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        PageResponse<CustomerResponse> customerResponses = customerService.getAll(page, size);
        return ResponseEntity.ok(customerResponses);
    }

    @PutMapping("/update-level/{id}")
    public ResponseEntity<CustomerResponse> updateLevel(@PathVariable String id){
        return ResponseEntity.ok().body(customerService.updateLevel(id));
    }
}
