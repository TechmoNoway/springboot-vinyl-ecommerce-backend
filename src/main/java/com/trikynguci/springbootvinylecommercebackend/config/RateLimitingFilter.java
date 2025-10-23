package com.trikynguci.springbootvinylecommercebackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory rate limiter for development/testing environments.
 * Implements a sliding window counter limiting requests per IP address.
 * 
 * Configuration:
 * - Window: 60 seconds (1 minute)
 * - Limit: 100 requests per window per IP
 * 
 * For production, consider:
 * - Redis-backed distributed rate limiting (Bucket4j + Redis)
 * - Per-user rate limiting (not just per-IP)
 * - Different limits for different endpoints
 * - Rate limit headers (X-RateLimit-Limit, X-RateLimit-Remaining)
 */
public class RateLimitingFilter extends HttpFilter {
    private static final long WINDOW_MS = 60_000L; // 1 minute
    private static final int LIMIT = 100; // requests per window

    private static class Window {
        long windowStart;
        int count;
    }

    private final Map<String, Window> store = new ConcurrentHashMap<>();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        String ip = getClientIP(req);
        long now = Instant.now().toEpochMilli();
        
        Window w = store.computeIfAbsent(ip, k -> {
            Window nw = new Window();
            nw.windowStart = now;
            nw.count = 0;
            return nw;
        });

        synchronized (w) {
            // Reset window if expired
            if (now - w.windowStart > WINDOW_MS) {
                w.windowStart = now;
                w.count = 0;
            }
            
            w.count++;
            
            if (w.count > LIMIT) {
                res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Rate limit exceeded. Please try again later.\"}");
                return;
            }
        }

        chain.doFilter(req, res);
    }

    /**
     * Get client IP considering X-Forwarded-For header (for proxied requests)
     */
    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
