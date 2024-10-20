package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse add(EmployeeRequest request);
    EmployeeResponse update(String id, EmployeeRequest request);
    PageResponse<EmployeeResponse> getAll(int page, int size);
    void delete(String id);
}
