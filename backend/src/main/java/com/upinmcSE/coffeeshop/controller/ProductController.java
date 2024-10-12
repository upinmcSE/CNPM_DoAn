package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.request.RecommendationRequest;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.dto.response.RecommendationResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.service.impl.ProductServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServiceImpl productService;
    private final RestTemplate restTemplate;

    @Value("${http.client.recommend}")
    String RECOMMENDATION_SERVICE_URL;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> add(
            @ModelAttribute ProductRequest request,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        ProductResponse productResponse = productService.add(request, file);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/getall")
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "9") int size
    ) {
        PageResponse<ProductResponse> productResponse = productService.getOutstandingProduct(page, size);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/getall-by-category")
    public ResponseEntity<PageResponse<ProductResponse>> getProductByCategory(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "9") int size,
            @RequestParam(value = "category") String category
    ) {
        PageResponse<ProductResponse> productResponse = productService.getCategoryProduct(page, size, category);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/recommend")
    public ResponseEntity<RecommendationResponse> getRecommendations(@RequestBody RecommendationRequest request) {
        String url = RECOMMENDATION_SERVICE_URL + "?customerId=" + request.customerId();

        ResponseEntity<RecommendationResponse> response = restTemplate.getForEntity(url, RecommendationResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                log.info(imageName + " not found");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving image: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-info/{productId}")
    public ResponseEntity<ProductResponse> updateInfo(
            @PathVariable("productId") String productId,
            @RequestBody ProductRequest request
    ) {
        ProductResponse productResponse = productService.updateInfo(productId, request);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/update-image/{productId}")
    public ResponseEntity<ProductResponse> updateImage(
            @PathVariable("productId") String productId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        ProductResponse productResponse = productService.updateImage(productId, file);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<SuccessResponse<?>> delete(@PathVariable("productId") String productId) {
        productService.delete(productId); // Assuming you have a delete method in ProductService
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Delete product successfully")
                .build());
    }
}
