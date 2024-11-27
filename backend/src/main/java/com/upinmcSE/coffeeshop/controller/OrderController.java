package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.HistoryResponse;
import com.upinmcSE.coffeeshop.dto.response.OrderOrderlineResponse;
import com.upinmcSE.coffeeshop.dto.response.OrderResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.OrderCache;
import com.upinmcSE.coffeeshop.entity.OrderLine;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.repository.OrderRepository;
import com.upinmcSE.coffeeshop.service.impl.CacheServiceImpl;
import com.upinmcSE.coffeeshop.service.impl.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderServiceImpl orderService;
    CacheServiceImpl cacheService;
    CustomerRepository customerRepository;
    OrderRepository orderRepository;

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

    // Xóa OrderLine từ Order
    @DeleteMapping("/remove-line/{orderId}/{orderLineId}")
    public ResponseEntity<String> removeOrderLine(@PathVariable String orderId, @PathVariable String orderLineId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        orderService.removeOrderLineFromOrder(customer.getId(), orderId, orderLineId);
        return ResponseEntity.ok("OrderLine đã được xóa khỏi Order.");
    }

    @GetMapping("/history")
    public ResponseEntity<PageResponse<HistoryResponse>> getHistory(@RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "4") int size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        return ResponseEntity.ok().body(orderService.getHistory(customer.getId(), page, size));
    }

    @GetMapping("/order-orderline")
    public ResponseEntity<List<OrderOrderlineResponse>> getOrderAndOrderLines() {
        List<Object[]> result = orderRepository.findOrderAndOrderLines();

        List<OrderOrderlineResponse> orderLineDTOs = new ArrayList<>();
        for (Object[] row : result) {
            OrderOrderlineResponse dto = new OrderOrderlineResponse();
            dto.setOrderId((String) row[0]);
            dto.setCustomerId((String) row[1]);
            dto.setTotalPrice((Double) row[2]);
            dto.setOrderLineId((String) row[3]);
            dto.setProductId((String) row[4]);
            dto.setAmount((Integer) row[5]);
            orderLineDTOs.add(dto);
        }
        return ResponseEntity.ok(orderLineDTOs);
    }

    @GetMapping("total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(orderService.calculateTotalRevenueForCurrentMonth());
    }
}
