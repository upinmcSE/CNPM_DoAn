package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
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

    @GetMapping("get-all")
    public ResponseEntity<List<EmployeeResponse>> getAll(){
        return ResponseEntity.ok().body(employeeService.getAll());
    }
    

}
