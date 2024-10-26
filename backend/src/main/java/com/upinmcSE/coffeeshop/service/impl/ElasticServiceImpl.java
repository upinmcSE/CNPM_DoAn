package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.entity.Product;
import com.upinmcSE.coffeeshop.entity.ProductDocument;
import com.upinmcSE.coffeeshop.repository.ProductElasticSearchRepository;
import com.upinmcSE.coffeeshop.repository.ProductRepository;
import com.upinmcSE.coffeeshop.service.ElasticService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticServiceImpl implements ElasticService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductElasticSearchRepository productElasticSearchRepository;

    @Transactional
    @Override // đòng bộ ữ liệu product :))
    public void syncProductsData() {
        List<Product> products = productRepository.findAll();

        List<ProductDocument> productDocuments = products.stream().map(product -> {
            ProductDocument productDocument = new ProductDocument();
            productDocument.setId(product.getId());
            productDocument.setName(product.getName());
            productDocument.setPrice(product.getPrice());
            productDocument.setDescription(product.getDescription());
            productDocument.setCategoryName(product.getCategory().getName());
            productDocument.setProductImageUrl(product.getProductImage().getImageUrl());
            productDocument.setInventoryQuantity(product.getInventory().getQuantity());
            return productDocument;
        }).toList();
        productElasticSearchRepository.saveAll(productDocuments);
    }
}
