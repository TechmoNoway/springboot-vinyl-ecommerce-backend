package com.trikynguci.springbootvinylecommercebackend.service;

import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;

public interface PaymentService {
    PaymentTransaction createPayment(String orderId, Long amount, String currency, String method, String idempotencyKey, String returnUrl);

    PaymentTransaction getLatestByOrderId(String orderId);

    void handleProviderCallback(String provider, String rawPayload);
}
