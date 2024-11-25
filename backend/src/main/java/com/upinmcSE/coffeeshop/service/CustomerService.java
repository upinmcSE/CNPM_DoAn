package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.request.CustomerUpdateRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse add(CustomerRequest request);
    CustomerResponse update(CustomerUpdateRequest request, Customer customer);
    PageResponse<CustomerResponse> getAll(int page, int size);
    CustomerResponse getById();
    void delete(String id);
}
