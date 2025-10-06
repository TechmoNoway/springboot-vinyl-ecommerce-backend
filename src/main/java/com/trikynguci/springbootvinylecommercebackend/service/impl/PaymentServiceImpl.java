package com.trikynguci.springbootvinylecommercebackend.service.impl;

import com.trikynguci.springbootvinylecommercebackend.mapper.PaymentTransactionMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderMapper;
import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;
import com.trikynguci.springbootvinylecommercebackend.payment.MomoProvider;
import com.trikynguci.springbootvinylecommercebackend.payment.VNPayProvider;
import com.trikynguci.springbootvinylecommercebackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final OrderMapper orderMapper;
    private final VNPayProvider vnPayProvider;
    private final MomoProvider momoProvider;

    @Override
    public PaymentTransaction createPayment(String orderId, Long amount, String currency, String method, String idempotencyKey, String returnUrl) {
        PaymentTransaction tx = PaymentTransaction.builder()
                .orderId(orderId)
                .provider(method)
                .amount(amount)
                .currency(currency == null ? "VND" : currency)
                .status("PENDING")
                .idempotencyKey(idempotencyKey)
                .createdAt(Instant.now())
                .build();

        paymentTransactionMapper.savePaymentTransaction(tx);

        // provider-specific URL
        String paymentUrl = null;
        if ("VNPAY".equalsIgnoreCase(method)) {
            paymentUrl = vnPayProvider.buildPaymentUrl(returnUrl, orderId, amount);
        } else if ("MOMO".equalsIgnoreCase(method)) {
            paymentUrl = momoProvider.buildPaymentUrl(returnUrl, orderId, amount);
        }

        // store request/response payload placeholders if needed (omitted here for brevity)
        PaymentTransaction created = paymentTransactionMapper.getById(tx.getId());
        // For convenience, we temporarily put the paymentUrl into responsePayload (not ideal long-term)
        if (paymentUrl != null) {
            created.setResponsePayload("{\"paymentUrl\":\"" + paymentUrl + "\"}");
        }
        return created;
    }

    @Override
    public PaymentTransaction getLatestByOrderId(String orderId) {
        return paymentTransactionMapper.getLatestByOrderId(orderId);
    }

    @Override
    public void handleProviderCallback(String provider, String rawPayload) {
        // Provider-specific parsing/verification should be done by provider class
        // For now this is a stub: you should parse provider payload, find transaction and update status.
    }
}

