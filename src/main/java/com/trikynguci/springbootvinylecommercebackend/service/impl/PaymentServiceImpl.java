package com.trikynguci.springbootvinylecommercebackend.service.impl;

import com.trikynguci.springbootvinylecommercebackend.mapper.PaymentTransactionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.OrderMapper;
import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;
import com.trikynguci.springbootvinylecommercebackend.payment.MomoProvider;
import com.trikynguci.springbootvinylecommercebackend.payment.VNPayProvider;
import com.trikynguci.springbootvinylecommercebackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final OrderMapper orderMapper;
    private final VNPayProvider vnPayProvider;
    private final MomoProvider momoProvider;
    private final com.trikynguci.springbootvinylecommercebackend.payment.ZaloPayProvider zaloPayProvider;

    @Override
    public PaymentTransaction createPayment(String orderId, Long amount, String currency, String method, String idempotencyKey, String returnUrl) {
        // idempotency: if a transaction with same orderId + idempotencyKey exists, return it
        if (idempotencyKey != null && !idempotencyKey.isEmpty()) {
            PaymentTransaction existing = paymentTransactionMapper.getByIdempotencyKey(orderId, idempotencyKey);
            if (existing != null) {
                return existing;
            }
        }

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
        } else if ("ZALOPAY".equalsIgnoreCase(method)) {
            paymentUrl = zaloPayProvider.buildPaymentUrl(returnUrl, orderId, amount);
        }

        // store request/response payload placeholders if needed (omitted here for brevity)
        PaymentTransaction created = paymentTransactionMapper.getById(tx.getId());
        // Persist paymentUrl directly
        if (paymentUrl != null) {
            paymentTransactionMapper.updatePaymentUrlById(created.getId(), paymentUrl, "{\"paymentUrl\":\"" + paymentUrl + "\"}");
            created.setPaymentUrl(paymentUrl);
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
        try {
            // parse rawPayload into map (supports query-string or JSON key=value pairs)
            Map<String, String> params = new java.util.HashMap<>();
            if (rawPayload == null) return;
            rawPayload = rawPayload.trim();
            if (rawPayload.startsWith("{") || rawPayload.startsWith("[")) {
                // JSON
                ObjectMapper om = new ObjectMapper();
                Map<String, Object> json = om.readValue(rawPayload, Map.class);
                for (Map.Entry<String, Object> e : json.entrySet()) {
                    params.put(e.getKey(), e.getValue() == null ? null : e.getValue().toString());
                }
            } else {
                // simple query string parsing: k1=v1&k2=v2
                String[] pairs = rawPayload.split("&");
                for (String p : pairs) {
                    int idx = p.indexOf('=');
                    if (idx > 0) {
                        String k = URLDecoder.decode(p.substring(0, idx), StandardCharsets.UTF_8);
                        String v = URLDecoder.decode(p.substring(idx + 1), StandardCharsets.UTF_8);
                        params.put(k, v);
                    }
                }
            }

            boolean verified = false;
            String providerTxId = null;
            String orderId = null;
            if ("VNPAY".equalsIgnoreCase(provider)) {
                verified = vnPayProvider.verifyCallback(params);
                providerTxId = params.get("vnp_TransactionNo");
                orderId = params.get("vnp_TxnRef");
                if (providerTxId == null) providerTxId = params.get("vnp_TransId");
            } else if ("MOMO".equalsIgnoreCase(provider)) {
                verified = momoProvider.verifyCallback(params);
                providerTxId = params.get("orderId"); // Momo may return partner's orderId and momo trans id in other fields
                orderId = params.get("orderId");
                if (params.get("transId") != null) providerTxId = params.get("transId");
            } else if ("ZALOPAY".equalsIgnoreCase(provider)) {
                verified = zaloPayProvider.verifyCallback(params);
                providerTxId = params.get("zp_transid");
                orderId = params.get("zp_orderid");
            }
            }

            if (!verified) {
                return;
            }

            // Find transaction: prefer provider_transaction_id, else lookup by orderId
            PaymentTransaction tx = null;
            if (providerTxId != null) {
                tx = paymentTransactionMapper.getByProviderTransactionId(provider, providerTxId);
            }
            if (tx == null && orderId != null) {
                tx = paymentTransactionMapper.getLatestByOrderId(orderId);
            }

            if (tx == null) return;

            // Update transaction to PAID if not already
            paymentTransactionMapper.updateStatusById(tx.getId(), "PAID", providerTxId, rawPayload);

            // Update order status to PAID as full immediate payment
            orderMapper.updateOrderStatus(tx.getOrderId(), "PAID");
        } catch (Exception ex) {
            // log and ignore - provider callbacks should be resilient
            // Use logging if available
            ex.printStackTrace();
        }
    }
}

