package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.request.CustomerUpdateRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.service.impl.CustomerServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerServiceImpl customerService;
    CustomerRepository customerRepository;

    @PostMapping("/add")
    public SuccessResponse<CustomerResponse> add(@RequestBody CustomerRequest request){
        System.out.println(request.password()+ "|" + request.rePassword());
        return SuccessResponse.<CustomerResponse>builder()
                .result(customerService.add(request))
                .build();
    }

    @GetMapping("/getall")
    public ResponseEntity<PageResponse<CustomerResponse>> getProducts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        PageResponse<CustomerResponse> customerResponses = customerService.getAll(page, size);
        return ResponseEntity.ok(customerResponses);
    }

//    @PutMapping("/update-level/{id}")
//    public ResponseEntity<CustomerResponse> updateLevel(@PathVariable String id){
//        return ResponseEntity.ok().body(customerService.updateLevel(id));
//    }

    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> update(@RequestBody CustomerUpdateRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        return ResponseEntity.ok().body(customerService.update(request, customer));
    }

    @GetMapping("/get")
    public ResponseEntity<CustomerResponse> getById(){
        return ResponseEntity.ok().body(customerService.getById());
    }
}
