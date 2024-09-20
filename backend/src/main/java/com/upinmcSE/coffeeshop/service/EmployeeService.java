package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse add(EmployeeRequest request);
    EmployeeResponse update(String id, EmployeeRequest request);

    List<EmployeeResponse> getAll();
    void delete(String id);
}
