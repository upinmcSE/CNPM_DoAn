package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.dto.response.CategoryResponse;
import com.upinmcSE.coffeeshop.entity.Category;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.CategoryMapper;
import com.upinmcSE.coffeeshop.repository.CategoryRepository;
import com.upinmcSE.coffeeshop.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Transactional
    @Override
    public String add(CategoryRequest request) {
        if(categoryRepository.existsByName(request.categoryName()))
            throw new ErrorException(ErrorCode.CATEGORY_EXISTED);
        Category category = Category.builder()
                .name(request.categoryName())
                .build();
        return categoryRepository.save(category).getId();
    }

    @Transactional
    @Override
    public CategoryResponse update(String id, CategoryRequest request) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CATEGORY));
        category.setName(request.categoryName());
        return categoryMapper.toCategoryRespose(category);
    }

    @Transactional
    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryRespose)
                .toList();
    }

    @Transactional
    @Override
    public void delete(String id) {
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CATEGORY)
        );

        categoryRepository.delete(category);
    }
}
