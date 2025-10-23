package com.trikynguci.springbootvinylecommercebackend.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Custom metrics service for application-specific measurements.
 * Provides counters and timers for business operations.
 */
@Component
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    /**
     * Record order creation event
     */
    public void recordOrderCreated(String status) {
        Counter.builder("orders.created")
                .description("Number of orders created")
                .tag("status", status)
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record payment transaction
     */
    public void recordPaymentTransaction(String provider, String status) {
        Counter.builder("payment.transactions")
                .description("Number of payment transactions")
                .tag("provider", provider)
                .tag("status", status)
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record payment transaction amount
     */
    public void recordPaymentAmount(String provider, double amount) {
        meterRegistry.summary("payment.amount")
                .record(amount);
    }

    /**
     * Time payment provider call
     */
    public Timer.Sample startPaymentTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Record payment provider call duration
     */
    public void recordPaymentDuration(Timer.Sample sample, String provider, String operation) {
        sample.stop(Timer.builder("payment.duration")
                .description("Payment provider call duration")
                .tag("provider", provider)
                .tag("operation", operation)
                .register(meterRegistry));
    }

    /**
     * Record authentication attempts
     */
    public void recordAuthAttempt(boolean success) {
        Counter.builder("auth.attempts")
                .description("Authentication attempts")
                .tag("result", success ? "success" : "failure")
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record API errors
     */
    public void recordError(String endpoint, String errorType) {
        Counter.builder("api.errors")
                .description("API errors")
                .tag("endpoint", endpoint)
                .tag("error_type", errorType)
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record cache hits/misses
     */
    public void recordCacheAccess(String cacheName, boolean hit) {
        Counter.builder("cache.access")
                .description("Cache access")
                .tag("cache", cacheName)
                .tag("result", hit ? "hit" : "miss")
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record email sent
     */
    public void recordEmailSent(String type, boolean success) {
        Counter.builder("email.sent")
                .description("Emails sent")
                .tag("type", type)
                .tag("result", success ? "success" : "failure")
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record webhook received
     */
    public void recordWebhookReceived(String provider, boolean verified) {
        Counter.builder("webhook.received")
                .description("Webhooks received")
                .tag("provider", provider)
                .tag("verified", verified ? "yes" : "no")
                .register(meterRegistry)
                .increment();
    }
}
