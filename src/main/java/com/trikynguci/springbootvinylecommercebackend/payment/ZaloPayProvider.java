package com.trikynguci.springbootvinylecommercebackend.payment;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ZaloPayProvider {

    public String buildPaymentUrl(String returnUrl, String orderId, Long amount) {
        // ZaloPay integration stub. Implement signature and request per ZaloPay docs.
        return "https://sandbox.zalopay.vn/checkout?zalo=...";
    }

    public boolean verifyCallback(Map<String, String> params) {
        // Verify callback signature per ZaloPay docs
        return true;
    }
}
