package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse add(ProductRequest request);
    ProductResponse update(String id, ProductRequest request);
    List<ProductResponse> getAll();
    void delete(String id);


}
