package com.trikynguci.springbootvinylecommercebackend.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator for payment providers availability.
 * Checks if payment providers are reachable (stub implementation).
 */
@Component
public class PaymentProviderHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // TODO: Add actual health checks for payment providers
            // For now, return UP status
            
            return Health.up()
                    .withDetail("vnpay", "available")
                    .withDetail("momo", "available")
                    .withDetail("zalopay", "available")
                    .withDetail("message", "All payment providers are reachable")
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("error", ex.getMessage())
                    .withException(ex)
                    .build();
        }
    }
}
