package com.trikynguci.springbootvinylecommercebackend.payment;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VNPayProvider {

    public String buildPaymentUrl(String returnUrl, String orderId, Long amount) {
        // Build VNPay payment URL per VNPay docs. This is a stub.
        return "https://sandbox.vnpayment.vn/vpcpay.html?vnp_Url=...";
    }

    public boolean verifyCallback(Map<String, String> params, String secureHash) {
        // Verify the secure hash according to VNPay doc.
        return true; // TODO: implement
    }
}
