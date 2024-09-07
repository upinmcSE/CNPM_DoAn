package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.dto.response.CategoryResponse;
import com.upinmcSE.coffeeshop.entity.Category;
import com.upinmcSE.coffeeshop.repository.CategoryRepository;
import com.upinmcSE.coffeeshop.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    @Override
    public String add(CategoryRequest request) {
        Category category = Category.builder()
                .categoryName(request.categoryName())
                .build();
        return categoryRepository.save(category).getId();
    }

    @Override
    public String update(String id, CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse getAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
