package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.dto.response.CategoryResponse;

public interface CategoryService {
    String add(CategoryRequest request);
    String update(String id, CategoryRequest request);
    CategoryResponse getAll();
    void delete(String id);
}
