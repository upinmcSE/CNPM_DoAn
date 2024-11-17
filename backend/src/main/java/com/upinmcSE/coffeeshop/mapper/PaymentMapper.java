package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.response.PaymentOrderResponse;
import com.upinmcSE.coffeeshop.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentOrderResponse toPaymentOrderResponse(Payment payment) {
        return PaymentOrderResponse.builder()
                .id(payment.getId())
                .customerId(payment.getOrder().getCustomer().getId())
                .orderId(payment.getOrder().getId())
                .paymentMethod(payment.getPaymentType().name())
                .status(payment.getStatus().name())
                .totalPrice(payment.getOrder().getTotalPrice())
                .build();
    }
}
