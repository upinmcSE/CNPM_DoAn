package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderCache;
import com.upinmcSE.coffeeshop.entity.OrderLine;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.service.impl.CacheServiceImpl;
import com.upinmcSE.coffeeshop.service.impl.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderServiceImpl orderService;
    CacheServiceImpl cacheService;
    CustomerRepository customerRepository;

    // Khởi tạo order
    @PostMapping("/create")
    public ResponseEntity<String> createOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        String orderId = orderService.createEmptyOrder(customer.getId());
        return ResponseEntity.ok().body(orderId);
    }

    // Thêm order line vào order
    @PostMapping("/add-line/{orderId}")
    public ResponseEntity<String> addOrderLine(@PathVariable String orderId, @RequestBody OrderLineRequest orderLine) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        System.out.println("orderLine xx: " + orderLine);
        orderService.addOrderLineToOrder(customer.getId(), orderLine);
        return ResponseEntity.ok("OrderLine đã được thêm vào Order.");
    }

    // API để xem Order từ Redis
    @GetMapping("/getOrder")
    public ResponseEntity<?> viewOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        OrderCache orderCache = cacheService.getOrderFromCache(customer.getId());

        if (orderCache != null) {
            // Trả về Order dưới dạng JSON
            return ResponseEntity.ok().body(orderCache);
        } else {
            // Trả về mã lỗi 404 nếu không tìm thấy Order
            return ResponseEntity.status(404).body("Order not found in Redis.");
        }
    }
}
