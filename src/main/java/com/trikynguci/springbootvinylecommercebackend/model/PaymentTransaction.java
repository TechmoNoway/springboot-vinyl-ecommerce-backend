package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTransaction {
    private Long id;
    private String orderId;
    private String provider; // VNPAY | MOMO | COD
    private String providerTransactionId;
    private String idempotencyKey;
    private Long amount;
    private String currency;
    private String status; // PENDING | PAID | FAILED | CANCELLED
    private String paymentMethod;
    private String requestPayload; // JSON string
    private String responsePayload; // JSON string
    private String callbackPayload; // JSON string
    private Integer attemptCount;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant paidAt;
}
