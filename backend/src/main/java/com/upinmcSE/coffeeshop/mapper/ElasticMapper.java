package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.entity.Product;
import com.upinmcSE.coffeeshop.entity.ProductDocument;
import org.springframework.stereotype.Component;

@Component
public class ElasticMapper {

    public ProductDocument toProductDocument(Product product){
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(product.getId());
        productDocument.setName(product.getName());
        productDocument.setPrice(product.getPrice());
        productDocument.setDescription(product.getDescription());
        productDocument.setCategoryName(product.getCategory().getName());
        productDocument.setProductImageUrl(product.getProductImage().getImageUrl());
        productDocument.setInventoryQuantity(product.getInventory().getQuantity());
        return productDocument;
    }
}
