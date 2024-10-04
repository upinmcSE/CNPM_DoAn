package com.upinmcSE.coffeeshop.configuration.momopay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoMoEndpoint {
    private String endpoint;
    private String create;
    private String refund;
    private String query;
    private String confirm;
    private String tokenPay;
    private String tokenBind;
    private String tokenCbInquiry;
    private String tokenDelete;

    public MoMoEndpoint(String endpoint, String create, String refund, String query, String confirm, String tokenPay, String tokenBind, String tokenQueryCb, String tokenDelete) {
        this.endpoint = endpoint;
        this.create = create;
        this.confirm = confirm;
        this.refund = refund;
        this.query = query;
        this.tokenPay = tokenPay;
        this.tokenBind = tokenBind;
        this.tokenCbInquiry = tokenQueryCb;
        this.tokenDelete = tokenDelete;
    }
}
