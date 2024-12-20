package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.PaymentInfo;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.PaymentOrderResponse;
import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.Payment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    PaymentResponse createPaymentVNPAY(String customerId, HttpServletRequest request, PaymentInfo paymentInfo);

    Map<String, Object> createPaymentMOMO(String orderId) throws Exception;

    String createPaymentCash(Customer customer, PaymentInfo paymentInfo);
    void completePayment(String choice, String paymentId);
    void checkPaymentPending();
    PageResponse<PaymentOrderResponse> getAll(int page, int size);
    void handCallBack(String orderInfo);

}
