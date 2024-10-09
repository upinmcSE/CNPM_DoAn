package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    String add(CategoryRequest request);
    CategoryResponse update(String id, CategoryRequest request);
    List<CategoryResponse> getAll();
    void delete(String id);
}
