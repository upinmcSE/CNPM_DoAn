package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse add(CustomerRequest request);
    CustomerResponse update(String id, CustomerRequest request);
    CustomerResponse updateLevel(String id );
    PageResponse<CustomerResponse> getAll(int page, int size);
    CustomerResponse getById();
    void delete(String id);
}
