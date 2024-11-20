package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.OrderLineRequest;
import com.upinmcSE.coffeeshop.dto.request.OrderRequest;
import com.upinmcSE.coffeeshop.dto.response.*;
import com.upinmcSE.coffeeshop.entity.*;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.OrderMapper;
import com.upinmcSE.coffeeshop.repository.*;
import com.upinmcSE.coffeeshop.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderLineRepository orderLineRepository;
    OrderMapper orderMapper;
    CustomerRepository customerRepository;
    CacheServiceImpl cacheService;
    ProductRepository productRepository;
    PaymentRepository paymentRepository;

    @Transactional
    @Override
    public String createEmptyOrder(String customerId) {
        // Kiem tra xem khach hang nay da co order trong redis chưa
        OrderCache existingOrderCache = cacheService.getOrderFromCache(customerId);
        if (existingOrderCache != null) {
            return existingOrderCache.getId();
        }

        // Tạo một đơn hàng rỗng
        OrderCache orderCache = new OrderCache();
        String orderId = UUID.randomUUID().toString();
        orderCache.setId(orderId);
        orderCache.setCustomerId(customerId);
        orderCache.setOrderLineCaches(new ArrayList<>());
        orderCache.setTotalPrice(0.0);

        // Lưu vào Redis dưới dạng OrderCache
        cacheService.saveOrderToCache(orderId, orderCache);
        return orderId;
    }


    @Transactional
    @Override // Thêm OrderLine vào Order trong Redis
    public void addOrderLineToOrder(String customerId, OrderLineRequest newOrderLine) {
        System.out.println("Adding order line...");

        // Retrieve OrderCache from Redis
        OrderCache orderCache = cacheService.getOrderFromCache(customerId);
        System.out.println("OrderCache xxx : "+orderCache);
        if (orderCache == null) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        // Check if product exists
        Product product = productRepository.findById(newOrderLine.productId())
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_PRODUCT));
        System.out.println("Product xx: "+product.getId());

        // Check for existing order line and update quantity if it exists
        boolean orderLineExists = false;

        for (OrderLineCache orderLine : orderCache.getOrderLineCaches()) {
            if (orderLine.getProduct().getId().equals(newOrderLine.productId())) {
                orderLine.setAmount(orderLine.getAmount() + newOrderLine.amount());
                orderLineExists = true;
                break;
            }
        }

        // If order line does not exist, add a new one with product price
        if (!orderLineExists) {
            OrderLineCache newLine = OrderLineCache.builder()
                    .id(UUID.randomUUID().toString())
                    .product(product)
                    .amount(newOrderLine.amount())
                    .build();
            orderCache.getOrderLineCaches().add(newLine);
        }

        System.out.println("OrderLine xx: "+orderCache);
        // Update total price using cached price in OrderLineCaches
        double updatedTotalPrice = orderCache.getOrderLineCaches().stream()
                .mapToDouble(line -> line.getProduct().getPrice() * line.getAmount())
                .sum();
        orderCache.setTotalPrice(updatedTotalPrice);
        System.out.println("Total price: "+updatedTotalPrice);

        // Save updated order in Redis
        cacheService.saveOrderToCache(orderCache.getId(), orderCache);
    }


    @Override
    public void removeOrderLineFromOrder(String customerID, String orderId, String orderLineId) {
        // Lấy Order từ cache
        OrderCache orderCache = cacheService.getOrderFromCache(customerID);

        // Kiểm tra nếu Order không tồn tại
        if (orderCache == null) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        // Tìm OrderLine cần xóa theo orderLineId
        OrderLineCache orderLineToRemove = orderCache.getOrderLineCaches().stream()
                .filter(line -> line.getId().equals(orderLineId))
                .findFirst()
                .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_ORDER_LINE));

        // Xóa OrderLine khỏi danh sách
        orderCache.getOrderLineCaches().remove(orderLineToRemove);

        // Tính toán lại tổng giá trị của Order sau khi xóa OrderLine
        double updatedTotal = orderCache.getOrderLineCaches().stream()
                .mapToDouble(line -> line.getProduct().getPrice() * line.getAmount())
                .sum();
        orderCache.setTotalPrice(updatedTotal);

        // Lưu lại Order đã cập nhật vào cache
        cacheService.saveOrderToCache(orderId, orderCache);
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByDay(LocalDate day) {
        return List.of();
    }

    @Override
    public PageResponse<HistoryResponse> getHistory(String customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        var data = orderRepository.findAllByCustomerId(customerId, pageable);

        if (data.isEmpty()) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }
        List<HistoryResponse> historyResponses = new ArrayList<>();
        for (Order order : data) {
            // Lấy status từ Payment
            var payment = paymentRepository.findByOrderId(order.getId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.NOT_FOUND_PAYMENT));

            HistoryResponse historyResponse = new HistoryResponse(
                    order.getId(),
                    order.getOrderLines().stream()
                            .map(orderLine -> new OrderLineResponse(
                                    orderLine.getId(),
                                    orderLine.getProduct().getName(),
                                    orderLine.getAmount()))
                            .toList(),
                    order.getTotalPrice(),
                    payment.getStatus(),
                    order.getCreatedDate(),
                    order.getModifiedDate()
            );

            historyResponses.add(historyResponse);
        }

        return PageResponse.<HistoryResponse>builder()
                .currentPage(page)
                .pageSize(data.getSize())
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .data(historyResponses)
                .build();
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByMonth(LocalDate month) {
        return List.of();
    }

    @Transactional
    @Override
    public List<OrderResponse> getAllByYear(LocalDate year) {
        return List.of();
    }

}
