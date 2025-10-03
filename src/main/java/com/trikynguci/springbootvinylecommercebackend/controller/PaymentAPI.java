package com.trikynguci.springbootvinylecommercebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trikynguci.springbootvinylecommercebackend.model.PaymentTransaction;
import com.trikynguci.springbootvinylecommercebackend.service.PaymentService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentAPI {

   private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> body) {
        String orderId = (String) body.get("orderId");
        Long amount = Long.valueOf(body.get("amount").toString());
        String method = (String) body.get("method");
        String idempotencyKey = (String) body.get("idempotencyKey");
        String returnUrl = (String) body.get("returnUrl");

        PaymentTransaction tx = paymentService.createPayment(orderId, amount, "VND", method, idempotencyKey, returnUrl);
        return ResponseEntity.ok(Map.of("status", "OK", "transactionId", tx.getId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getStatus(@PathVariable String orderId) {
        PaymentTransaction tx = paymentService.getLatestByOrderId(orderId);
        if (tx == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tx);
    }

    @PostMapping("/webhook/{provider}")
    public ResponseEntity<?> webhook(@PathVariable String provider, @RequestBody String rawPayload) {
        paymentService.handleProviderCallback(provider.toUpperCase(), rawPayload);
        return ResponseEntity.ok().build();
    }

}
