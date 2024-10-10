package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.configuration.momo.MoMoConfig;
import com.upinmcSE.coffeeshop.configuration.vnpay.VNPAYConfig;
import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.entity.Payment;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.repository.OrderRepository;
import com.upinmcSE.coffeeshop.repository.PaymentRepository;
import com.upinmcSE.coffeeshop.service.PaymentService;
import com.upinmcSE.coffeeshop.utils.vnpay.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;
    VNPAYConfig vnpayConfig;
    MoMoConfig moMoConfig;
    @Override
    public PaymentResponse createPaymentVNPAY(HttpServletRequest request) {
        Order order = orderRepository.findById(request.getParameter("orderId")).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ORDER)
        );

        long amount = Math.round(order.getTotalPrice() * 100L);
//        System.out.println(Math.round(order.getTotalPrice()));
//        System.out.println(amount);
        String bankCode = "NCB";

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: "+ order.getId());
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

    @Override
    public void handCallBack(String orderInfo) {
        System.out.println(orderInfo);
        String prefix = "Thanh toan don hang: ";
        int startIndex = orderInfo.indexOf(prefix) + prefix.length();

        String orderId = orderInfo.substring(startIndex, startIndex + 36);
        var order  = orderRepository.findById(orderId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ORDER));

        paymentRepository.save(Payment.builder().order(order).build());
    }

}
