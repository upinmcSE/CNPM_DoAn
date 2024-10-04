package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .urlImage(product.getProductImages().getImageUrl())
                .build();
    }
}
