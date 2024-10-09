package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.dto.response.ResponseObject;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.dto.response.VNPayResponse;
import com.upinmcSE.coffeeshop.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    RestTemplate restTemplate = new RestTemplate();
    PaymentServiceImpl paymentService;

    @GetMapping("/vn-pay")
    public SuccessResponse<PaymentResponse> pay(HttpServletRequest request){
        return SuccessResponse.<PaymentResponse>builder()
                .message("Payment successfully !!")
                .result(paymentService.createPaymentVNPAY(request))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<VNPayResponse> vnPayCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseObject<>(HttpStatus.OK, "Success", new VNPayResponse("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }

    @PostMapping("/momo")
    public ResponseEntity<?> createPayment() {
        try {

            String orderId = "637ad6bf-5209-478c-946e-4fd3afa41fd7";

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


}
