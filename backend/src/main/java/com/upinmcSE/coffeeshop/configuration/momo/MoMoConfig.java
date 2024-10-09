package com.upinmcSE.coffeeshop.configuration.momo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class MoMoConfig {
    @Value("${payment.momo.partnerCode}")
    private String partnerCode;

    @Value("${payment.momo.accessKey}")
    private String accessKey;

    @Value("${payment.momo.secretKey}")
    private String secretKey;

    @Value("${payment.momo.orderInfo}")
    private String orderInfo;

    @Value("${payment.momo.redirectUrl}")
    private String redirectUrl;

    @Value("${payment.momo.ipnUrl}")
    private String ipnUrl;

    @Value("${payment.momo.requestType}")
    private String requestType;

    @Value("${payment.momo.extraData}")
    private String extraData;

    @Value("${payment.momo.lang}")
    private String lang;

    @Value("${payment.momo.autoCapture}")
    private boolean autoCapture;

    @Value("${payment.momo.orderGroupId}")
    private String orderGroupId;

    public Map<String, Object> getMoMoConfig(String orderId,String amount) throws Exception {
        String requestId = orderId;

        // Tạo chữ ký
        String rawSignature = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature = hmacSHA256(secretKey, rawSignature);

        // Tạo request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("partnerCode", partnerCode);
        requestBody.put("partnerName", "Test");
        requestBody.put("storeId", "MomoTestStore");
        requestBody.put("requestId", requestId);
        requestBody.put("orderId", orderId);
        requestBody.put("amount", amount);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("redirectUrl", redirectUrl);
        requestBody.put("ipnUrl", ipnUrl);
        requestBody.put("lang", lang);
        requestBody.put("requestType", requestType);
        requestBody.put("autoCapture", autoCapture);
        requestBody.put("extraData", extraData);
        requestBody.put("orderGroupId", orderGroupId);
        requestBody.put("signature", signature);

        return requestBody;
    }

    private String hmacSHA256(String key, String data) throws Exception {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKeySpec);
        return bytesToHex(sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
