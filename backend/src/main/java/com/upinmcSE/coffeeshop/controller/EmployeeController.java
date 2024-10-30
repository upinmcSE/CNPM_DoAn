package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.request.EmployeeUpdateRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.service.impl.EmployeeServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeServiceImpl employeeService;

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request){
        return ResponseEntity.ok().body(employeeService.add(request));
    }

    @GetMapping("/getall")
    public ResponseEntity<PageResponse<EmployeeResponse>> getEmployees(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        PageResponse<EmployeeResponse> employeeResponses = employeeService.getAll(page, size);
        return ResponseEntity.ok(employeeResponses);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable String id, @RequestBody EmployeeUpdateRequest request){
        return ResponseEntity.ok().body(employeeService.update(id, request));
    }

}
