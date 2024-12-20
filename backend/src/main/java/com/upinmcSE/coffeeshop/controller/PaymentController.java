package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.PaymentInfo;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.PaymentOrderResponse;
import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.Payment;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    RestTemplate restTemplate = new RestTemplate();
    PaymentServiceImpl paymentService;
    CustomerRepository customerRepository;

    @GetMapping("/vn-pay")
    public SuccessResponse<PaymentResponse> pay(
            HttpServletRequest request,
            @RequestParam String phone,
            @RequestParam String address
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        return SuccessResponse.<PaymentResponse>builder()
                .message("Payment successfully !!")
                .result(paymentService.createPaymentVNPAY(customer.getId(), request, PaymentInfo.builder()
                                .address(address)
                                .phoneNumber(phone)
                        .build()))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public RedirectView vnPayCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String amount = request.getParameter("vnp_Amount");
        String orderInfo = request.getParameter("vnp_OrderInfo");

        if (status.equals("00")) {
            paymentService.handCallBack(orderInfo);
            return new RedirectView("http://localhost:5173/success");
        } else {
            return new RedirectView("http://localhost:5173/payment-failure");
        }
    }

    @PostMapping("/momo")
    public ResponseEntity<?> createPayment() {
        try {

            String orderId = "61dc575b-7943-464e-aa49-27d004a909d3";

            // Tạo request body cho MoMo
            Map<String, Object> requestBody = paymentService.createPaymentMOMO(orderId);

            // Gửi request tới MoMo API
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://test-payment.momo.vn/v2/gateway/api/create",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody Map<String, Object> requestBody) {
        System.out.println("Callback data: " + requestBody);
        return ResponseEntity.status(204).body(requestBody);
    }

//    @PostMapping("/check-status-transaction")
//    public ResponseEntity<?> checkTransactionStatus(@RequestBody Map<String, String> requestBody) {
//        String orderId = requestBody.get("orderId");
//
//        try {
//            String rawSignature = "accessKey=" + config.accessKey +
//                    "&orderId=" + orderId +
//                    "&partnerCode=" + config.partnerCode +
//                    "&requestId=" + orderId;
//
//            String signature = hmacSHA256(config.secretKey, rawSignature);
//
//            Map<String, Object> queryRequestBody = new HashMap<>();
//            queryRequestBody.put("partnerCode", config.partnerCode);
//            queryRequestBody.put("requestId", orderId);
//            queryRequestBody.put("orderId", orderId);
//            queryRequestBody.put("signature", signature);
//            queryRequestBody.put("lang", config.lang);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/json");
//
//            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(queryRequestBody, headers);
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "https://test-payment.momo.vn/v2/gateway/api/query",
//                    HttpMethod.POST,
//                    entity,
//                    String.class
//            );
//
//            return ResponseEntity.ok(response.getBody());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error: " + e.getMessage());
//        }
//    }

    @PostMapping("/cash")
    public SuccessResponse<?> cashPayment(
            @RequestParam String phone,
            @RequestParam String address
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        return SuccessResponse.builder().message("Payment Successfully")
                .result(paymentService.createPaymentCash(customer, PaymentInfo.builder()
                        .address(address)
                        .phoneNumber(phone)
                        .build()))
                .build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<PaymentOrderResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok().body(paymentService.getAll(page, size));
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestParam String choice, @RequestParam String paymentId) {
        paymentService.completePayment(choice, paymentId);
        return ResponseEntity.ok().body("Complete payment successfully");
    }
}
