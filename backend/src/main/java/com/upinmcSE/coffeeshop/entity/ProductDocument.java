package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "product")
public class ProductDocument {
    @Id
    private String id;
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private String productImageUrl;
    private int inventoryQuantity;
}
