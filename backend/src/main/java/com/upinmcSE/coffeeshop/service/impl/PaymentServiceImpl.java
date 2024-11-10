package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.configuration.momo.MoMoConfig;
import com.upinmcSE.coffeeshop.configuration.vnpay.VNPAYConfig;
import com.upinmcSE.coffeeshop.dto.request.PaymentInfo;
import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.entity.*;
import com.upinmcSE.coffeeshop.enums.PaymentType;
import com.upinmcSE.coffeeshop.enums.Status;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.repository.OrderRepository;
import com.upinmcSE.coffeeshop.repository.PaymentRepository;
import com.upinmcSE.coffeeshop.service.PaymentService;
import com.upinmcSE.coffeeshop.utils.vnpay.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;
    VNPAYConfig vnpayConfig;
    MoMoConfig moMoConfig;
    CacheServiceImpl cacheService;
    CustomerRepository customerRepository;

    @Override
    public PaymentResponse createPaymentVNPAY(String customerId, HttpServletRequest request, PaymentInfo paymentInfo) {
        OrderCache orderCache = cacheService.getOrderFromCache(customerId);

        long amount = Math.round(orderCache.getTotalPrice() * 100L);
//        System.out.println(Math.round(order.getTotalPrice()));
//        System.out.println(amount);
        String bankCode = "NCB";
        System.out.println(orderCache);
        System.out.println(paymentInfo);

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: "+ orderCache.getId() + "|" + customerId +"|" + paymentInfo.phoneNumber() + "|" + paymentInfo.address());
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_BankCode", bankCode);
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentResponse.builder()
                .paymentUrl(paymentUrl).build();
    }

    @Override
    public Map<String, Object> createPaymentMOMO(String orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ORDER));
        String amount = String.valueOf(Math.round(order.getTotalPrice()));
        System.out.println(amount);

        return moMoConfig.getMoMoConfig(orderId, amount);

    }

    @Transactional
    @Override
    public String createPaymentCash(Customer customer, PaymentInfo paymentInfo) {
        OrderCache orderCache = cacheService.getOrderFromCache(customer.getId());

        // handle payment cash
        List<OrderLine> orderLines = new ArrayList<>();
        orderCache.getOrderLineCaches().forEach(orderLine -> {
            orderLines.add(OrderLine.builder()
                    .product(orderLine.getProduct())
                    .amount(orderLine.getAmount())
                    .build());
        });
        Order order = Order.builder()
                .customer(customer)
                .orderLines(orderLines)
                .totalPrice(orderCache.getTotalPrice())
                .build();

        orderLines.forEach(line -> line.setOrder(order));

        orderRepository.save(order);
        Payment payment = Payment.builder()
                .order(order)
                .paymentType(PaymentType.CASH)
                .phoneNumber(paymentInfo.phoneNumber())
                .address(paymentInfo.address())
                .status(Status.PENDING)
                .build();
        payment = paymentRepository.save(payment);

        cacheService.removeOrderFromCache(orderCache.getId());
        return payment.getId();

    }

    @Transactional
    @Override
    public void completePayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_PAYMENT));
        payment.setStatus(Status.COMPLETED);
    }

    @Scheduled(fixedRate = 3600000) // dat lịch kiem tra he thong moi 1h
    @Transactional
    @Override
    public void checkPaymentPending() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoHoursAgo = now.minusHours(2);

        // Lấy tất cả các thanh toán đang ở trạng thái PENDING và đã quá 2 giờ
        List<Payment> pendingPayments = paymentRepository
                .findAllByStatusAndCreatedTimeBefore(Status.PENDING, twoHoursAgo);

        for (Payment payment : pendingPayments) {
            payment.setStatus(Status.CANCELLED); // Chuyển sang trạng thái CANCELLED
            paymentRepository.save(payment);
        }
    }

    @Override
    public void handCallBack(String orderInfo) {
        System.out.println(orderInfo);
        String prefix = "Thanh toan don hang: ";
        int startIndex = orderInfo.indexOf(prefix) + prefix.length();


        String[] parts = orderInfo.split("\\|");

        // Lấy phần tử thứ 2 (index 1) từ mảng parts
        String customerId = parts.length > 1 ? parts[1] : "";
        String phoneNumber = parts.length > 2 ? parts[2] : "";
        String address = parts.length > 3 ? parts[3] : "";

        String orderId = orderInfo.substring(startIndex, startIndex + 36);
        OrderCache orderCache = cacheService.getOrderFromCache(customerId);
        if (!orderId.equals(orderCache.getId())) {
            throw new ErrorException(ErrorCode.NOT_FOUND_ORDER);
        }

        // handle payment success
        List<OrderLine> orderLines = new ArrayList<>();
        orderCache.getOrderLineCaches().forEach(orderLine -> {
            orderLines.add(OrderLine.builder()
                    .product(orderLine.getProduct())
                    .amount(orderLine.getAmount())
                    .build());
        });
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        Order order = Order.builder()
                .customer(customer)
                .orderLines(orderLines)
                .totalPrice(orderCache.getTotalPrice())
                .build();

        orderLines.forEach(line -> line.setOrder(order));

        orderRepository.save(order);
        Payment payment = Payment.builder()
                .order(order)
                .paymentType(PaymentType.VNPAY)
                .address(address)
                .phoneNumber(phoneNumber)
                .status(Status.COMPLETED)
                .build();
        paymentRepository.save(payment);
        cacheService.removeOrderFromCache(orderCache.getId());
    }

}
