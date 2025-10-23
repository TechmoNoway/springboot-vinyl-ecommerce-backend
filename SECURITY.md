# Security Configuration

This document describes the security features implemented in this Spring Boot application.

## Features

### 1. Authentication & Authorization

- **JWT-based authentication**: Stateless authentication using JSON Web Tokens
- **Method-level security**: `@EnableMethodSecurity` allows fine-grained access control with annotations
- **Public endpoints**: Auth, products, categories, and payment endpoints are publicly accessible
- **Protected endpoints**: All other endpoints require valid JWT authentication

### 2. CORS (Cross-Origin Resource Sharing)

**Current Configuration (Development)**:
- Allows all origins (`*` pattern)
- Allows credentials
- Supports all standard HTTP methods
- 1-hour preflight cache

**Production Recommendations**:
```java
configuration.setAllowedOrigins(Arrays.asList(
    "https://yourdomain.com",
    "https://app.yourdomain.com"
));
```

### 3. Rate Limiting

**Implementation**: In-memory sliding window counter per IP address

**Current Limits (Development)**:
- Window: 60 seconds
- Limit: 100 requests per IP per window
- Returns HTTP 429 (Too Many Requests) when exceeded

**Production Recommendations**:
- Use distributed rate limiting (Redis + Bucket4j)
- Implement per-user rate limiting (not just per-IP)
- Different limits for different endpoints:
  ```
  /api/v1/auth/login: 5 req/min
  /api/v1/products: 100 req/min
  /api/v1/orders: 20 req/min
  ```
- Add rate limit headers:
  ```
  X-RateLimit-Limit: 100
  X-RateLimit-Remaining: 45
  X-RateLimit-Reset: 1635724800
  ```

### 4. CSRF Protection

**Current Status**: Disabled for stateless JWT API

**When to enable**:
- If using session-based authentication
- If using cookies for authentication
- For traditional server-rendered forms

**How to enable** (if needed):
```java
.csrf(csrf -> csrf
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
)
```

### 5. Security Headers

Spring Security automatically adds:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`

**Additional headers to consider** (add via filter or Spring Security):
```
Strict-Transport-Security: max-age=31536000; includeSubDomains
Content-Security-Policy: default-src 'self'
Referrer-Policy: strict-origin-when-cross-origin
Permissions-Policy: geolocation=(), microphone=(), camera=()
```

## Security Checklist for Production

### Before Deployment

- [ ] Update CORS configuration to allow only specific origins
- [ ] Implement distributed rate limiting (Redis/Resilience4j)
- [ ] Add per-user rate limits
- [ ] Configure different rate limits for different endpoints
- [ ] Enable HTTPS/TLS (let Spring Security handle HSTS)
- [ ] Review and minimize public endpoints
- [ ] Add security headers (CSP, HSTS, etc.)
- [ ] Implement request/response logging for security events
- [ ] Set up alerts for:
  - Rate limit violations
  - Failed authentication attempts
  - Suspicious IP patterns
- [ ] Conduct security audit/penetration testing
- [ ] Review JWT token expiration times
- [ ] Implement JWT token rotation/refresh strategy
- [ ] Add IP whitelisting for admin endpoints (if applicable)
- [ ] Configure secure password policies
- [ ] Enable database encryption at rest
- [ ] Review and update dependency vulnerabilities

### Monitoring & Logging

Monitor these security metrics:
- Failed login attempts per IP/user
- Rate limit violations
- JWT validation failures
- Unusual API access patterns
- Payment webhook signature failures

### Testing Security

```bash
# Test rate limiting
for i in {1..150}; do curl http://localhost:8080/api/v1/products; done

# Test CORS
curl -H "Origin: http://example.com" \
     -H "Access-Control-Request-Method: POST" \
     -X OPTIONS http://localhost:8080/api/v1/auth/login

# Test JWT authentication
curl -H "Authorization: Bearer invalid_token" \
     http://localhost:8080/api/v1/orders
```

## Environment Variables

Recommended security-related environment variables:

```properties
# JWT Configuration
JWT_SECRET_KEY=${JWT_SECRET_KEY:changeme}
JWT_EXPIRATION=${JWT_EXPIRATION:3600000}
JWT_REFRESH_TOKEN_EXPIRATION=${JWT_REFRESH_TOKEN_EXPIRATION:86400000}

# CORS (production)
ALLOWED_ORIGINS=${ALLOWED_ORIGINS:https://yourdomain.com}

# Rate Limiting (production)
RATE_LIMIT_WINDOW_SEC=${RATE_LIMIT_WINDOW_SEC:60}
RATE_LIMIT_MAX_REQUESTS=${RATE_LIMIT_MAX_REQUESTS:100}

# Redis (for distributed rate limiting)
REDIS_HOST=${REDIS_HOST:localhost}
REDIS_PORT=${REDIS_PORT:6379}
```

## References

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
- [CORS Best Practices](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
