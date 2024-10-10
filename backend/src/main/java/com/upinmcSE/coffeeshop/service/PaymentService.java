package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    PaymentResponse createPaymentVNPAY(HttpServletRequest request);

    Map<String, Object> createPaymentMOMO(String orderId) throws Exception;

    void handCallBack(String orderInfo);

}
