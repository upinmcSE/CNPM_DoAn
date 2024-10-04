package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse add(ProductRequest request, MultipartFile file) throws IOException;
    ProductResponse update(String id, ProductRequest request);

    PageResponse<ProductResponse> getOutstandingProduct(int page, int size);
    PageResponse<ProductResponse> getCategoryProduct(int page, int size, String category);
    ProductResponse getRecommemdProduct();
    
    void delete(String id);


}
