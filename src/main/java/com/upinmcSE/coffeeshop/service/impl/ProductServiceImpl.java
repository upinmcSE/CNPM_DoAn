package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.Category;
import com.upinmcSE.coffeeshop.entity.Product;
import com.upinmcSE.coffeeshop.mapper.ProductMapper;
import com.upinmcSE.coffeeshop.repository.CategoryRepository;
import com.upinmcSE.coffeeshop.repository.ProductRepository;
import com.upinmcSE.coffeeshop.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public ProductResponse add(ProductRequest request) {
        Category category = categoryRepository.findByCategoryName(request.category());

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(category)
                .build();
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        return null;
    }

    @Override
    public List<ProductResponse> getAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
