package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.service.impl.ProductServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductServiceImpl productService;

    @PostMapping(value="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> add(
            @ModelAttribute ProductRequest request,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok().body(productService.add(request, file));
    }
    @GetMapping("/getall")
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size

    ){
        return ResponseEntity.ok().body(productService.getOutstandingProduct(page, size));
    }

    @GetMapping("/getall-by-category")
    public ResponseEntity<PageResponse<ProductResponse>> getProductByCategory(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "category") String category
    ){
        return ResponseEntity.ok().body(productService.getCategoryProduct(page, size, category));
    }
}
