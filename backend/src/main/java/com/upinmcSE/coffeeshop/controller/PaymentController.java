package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.dto.response.ResponseObject;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.dto.response.VNPayResponse;
import com.upinmcSE.coffeeshop.dto.response.momo.PaymentMoMoResponse;
import com.upinmcSE.coffeeshop.exception.MoMoException;
import com.upinmcSE.coffeeshop.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentServiceImpl paymentService;

    @GetMapping("/vn-pay")
    public SuccessResponse<PaymentResponse> pay(HttpServletRequest request){
        return SuccessResponse.<PaymentResponse>builder()
                .message("Payment successfully !!")
                .result(paymentService.createPayment(request))
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
    public SuccessResponse<PaymentMoMoResponse> payMoMO(HttpServletRequest request) throws MoMoException {
        return SuccessResponse.<PaymentMoMoResponse>builder()
                .message("Payment successfully !!")
                .result(paymentService.createMoMoPayment(request))
                .build();
    }

    @PostMapping("/momo-callback")
    public ResponseObject<String> momoCallbackHandler(HttpServletRequest request){
        return new ResponseObject<>(HttpStatus.OK,"ahihi",null);
    }


}
