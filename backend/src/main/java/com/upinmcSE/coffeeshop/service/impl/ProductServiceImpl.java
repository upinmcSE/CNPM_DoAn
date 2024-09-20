package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.ProductRequest;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.Category;
import com.upinmcSE.coffeeshop.entity.Product;
import com.upinmcSE.coffeeshop.entity.ProductImage;
import com.upinmcSE.coffeeshop.mapper.ProductMapper;
import com.upinmcSE.coffeeshop.repository.CategoryRepository;
import com.upinmcSE.coffeeshop.repository.ProductImageRepository;
import com.upinmcSE.coffeeshop.repository.ProductRepository;
import com.upinmcSE.coffeeshop.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;
    ProductImageRepository productImageRepository;

    @Override
    public ProductResponse add(ProductRequest request, MultipartFile file) throws IOException {
        // Tìm loại sản phẩm
        Category category = categoryRepository.findByCategoryName(request.category());

        // tạo 1 đối tượng product
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(category)
                .build();

        // lưu product xuống db
        product = productRepository.save(product);

        // tạo product image
        ProductImage productImage = createProductImage(product.getId(), file);

        product.setProductImages(productImage);
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        return null;
    }

    @Override
    public PageResponse<ProductResponse> getOutstandingProduct(int page, int size) {
        return null;
    }

    @Override
    public PageResponse<ProductResponse> getCategoryProduct(int page, int size) {
        return null;
    }

    @Override
    public ProductResponse getRecommemdProduct() {
        return null;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    private ProductImage createProductImage(String productId, MultipartFile file) throws IOException {
        // Tìm product theo productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra file ảnh có rỗng không
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Kiểm tra kích thước file và định dạng
        if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
            throw new RuntimeException("File size is too large");
        }

        // Kiểm tra xem có phải file ảnh không
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Invalid file type, only image files are accepted");
        }

        // Lưu file vào hệ thống và lấy tên file
        String filename = storeFile(file);

        // Kiểm tra xem sản phẩm đã có ảnh chưa (One-to-One relationship)
        ProductImage existingProductImage = product.getProductImages();
        if (existingProductImage != null) {
            // Xóa ảnh cũ nếu đã tồn tại
            productImageRepository.delete(existingProductImage);
        }

        // Tạo mới ProductImage
        ProductImage productImage = ProductImage.builder()
                .product(product)
                .imageUrl(filename)
                .build();

        // Lưu ảnh vào cơ sở dữ liệu
        return productImageRepository.save(productImage);
    }


    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        // Đường dẫn đến thư mục mà bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");

        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
