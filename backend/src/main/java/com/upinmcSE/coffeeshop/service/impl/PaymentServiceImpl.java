package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.configuration.momopay.CustomEnvironment;
import com.upinmcSE.coffeeshop.configuration.vnpay.VNPAYConfig;
import com.upinmcSE.coffeeshop.dto.response.PaymentResponse;
import com.upinmcSE.coffeeshop.dto.response.momo.PaymentMoMoResponse;
import com.upinmcSE.coffeeshop.entity.Order;
import com.upinmcSE.coffeeshop.enums.momo.RequestType;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.exception.MoMoException;
import com.upinmcSE.coffeeshop.repository.OrderRepository;
import com.upinmcSE.coffeeshop.service.PaymentService;
import com.upinmcSE.coffeeshop.service.momo.CreateOrderMoMo;
import com.upinmcSE.coffeeshop.utils.momopay.LogUtils;
import com.upinmcSE.coffeeshop.utils.vnpay.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    OrderRepository orderRepository;
    VNPAYConfig vnpayConfig;
    CreateOrderMoMo createOrderMoMo;
    @Override
    public PaymentResponse createPayment(HttpServletRequest request) {
        Order order = orderRepository.findById(request.getParameter("orderId")).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ORDER)
        );

        long amount = Math.round(order.getTotalPrice() * 100L);
//        System.out.println(Math.round(order.getTotalPrice()));
//        System.out.println(amount);
        String bankCode = request.getParameter("bankCode");

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: "+ order.getId());
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if(bankCode != null & bankCode.isEmpty()){ 
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
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
    public PaymentMoMoResponse createMoMoPayment(HttpServletRequest request) throws MoMoException {
        Order order = orderRepository.findById(request.getParameter("orderId")).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ORDER));
        String orderId = order.getId();
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        long amount = Math.round(order.getTotalPrice());

        String payUrl = "https://momo.vn/payment?orderId="+orderId; // Link thanh toán từ MoMo
        String shortLink = "https://momo.vn/s/"+orderId;
        String deeplink = "momo://payment?orderId="+orderId;

        String partnerClientId = "partnerClientId";
        String orderInfo = "Pay With MoMo";
        String returnUrl = "http://localhost:8081/coffee/api/v1/payment/momo-callback";
        String notifyUrl = "https://google.com.vn";

        CustomEnvironment environment = CustomEnvironment.selectEnv("dev");
        PaymentMoMoResponse captureWalletMoMoResponse = createOrderMoMo.process(environment, order.getId(), requestId,
                Long.toString(amount), orderInfo, returnUrl, notifyUrl,"", RequestType.PAY_WITH_ATM, Boolean.TRUE);

        return captureWalletMoMoResponse;
    }



}
