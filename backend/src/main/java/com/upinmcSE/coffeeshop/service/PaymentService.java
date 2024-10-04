package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.dto.response.momo.PaymentMoMoResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentResponse createPayment(HttpServletRequest request);

    PaymentMoMoResponse createMoMoPayment(HttpServletRequest request) throws Exception;


}
