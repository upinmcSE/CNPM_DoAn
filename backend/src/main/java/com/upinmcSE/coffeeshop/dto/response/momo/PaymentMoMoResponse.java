package com.upinmcSE.coffeeshop.dto.response.momo;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class PaymentMoMoResponse extends Response{
    public PaymentMoMoResponse(Integer resultCode, String message, String payUrl) {
        this.resultCode = resultCode;
        this.message = message;
        this.payUrl = payUrl;
    }

    private String requestId;

    private Long amount;

    private String payUrl;

    private String shortLink;

    private String deeplink;

    private String qrCodeUrl;

    private String deeplinkWebInApp;

    private Long transId;

    private String applink;

    private String partnerClientId;

    private String bindingUrl;

    private String deeplinkMiniApp;

}
