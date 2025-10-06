package com.trikynguci.springbootvinylecommercebackend.payment;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MomoProvider {

    public String buildPaymentUrl(String returnUrl, String orderId, Long amount) {
        // Build Momo payment URL or QR payload per Momo docs. This is a stub.
        return "https://test-payment.momo.vn/gw_payment/transactionProcessor";
    }

    public boolean verifyCallback(Map<String, String> params, String signature) {
        // Verify signature according to Momo docs.
        return true; // TODO: implement
    }
}
