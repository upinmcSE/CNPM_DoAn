package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse add(CustomerRequest request);
    CustomerResponse update(String id, CustomerRequest request);
    List<CustomerResponse> getAll();
    void delete(String id);
}
