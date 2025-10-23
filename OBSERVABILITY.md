# Observability Guide

This document describes the observability features implemented in the Spring Boot Vinyl E-Commerce Backend.

## Overview

The application includes comprehensive logging, metrics, and health monitoring capabilities:

- **Structured JSON Logging** via Logstash Logback Encoder
- **Metrics & Monitoring** via Micrometer + Prometheus
- **Health Checks** via Spring Boot Actuator
- **Request Tracing** via MDC (Mapped Diagnostic Context)
- **Custom Application Metrics** for business operations

## Structured Logging

### Configuration

Logging is configured in `src/main/resources/logback-spring.xml`:

- **Development**: Human-readable console logs with color coding
- **Production**: JSON-formatted logs to console and rolling files
- **Test**: Minimal console output

### Log Formats

**Development (Console):**
```
2025-10-23T15:30:45.123+07:00  INFO 12345 --- [nio-4242-exec-1] c.t.s.controller.OrderAPI : Order created: ORD-123
```

**Production (JSON):**
```json
{
  "@timestamp": "2025-10-23T15:30:45.123+07:00",
  "level": "INFO",
  "thread": "http-nio-4242-exec-1",
  "logger": "com.trikynguci.springbootvinylecommercebackend.controller.OrderAPI",
  "message": "Order created: ORD-123",
  "application": "vinyl-ecommerce-backend",
  "environment": "prod",
  "requestId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "userId": "user123",
  "orderId": "ORD-123"
}
```

### MDC Context Fields

The application adds contextual information to logs via MDC:

- `requestId` - Unique identifier for each HTTP request
- `userId` - Authenticated user ID (if available)
- `orderId` - Order ID for order-related operations
- `transactionId` - Payment transaction ID
- `traceId` - Distributed tracing ID (if tracing is enabled)
- `spanId` - Span ID for distributed tracing

### Using Logging in Code

```java
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@Service
public class OrderService {
    
    public void createOrder(OrderRequest request) {
        // Add context to MDC
        MDC.put("orderId", request.getOrderId());
        
        log.info("Creating order: {}", request.getOrderId());
        
        try {
            // Business logic
            log.debug("Order items: {}", request.getItems());
        } catch (Exception ex) {
            log.error("Failed to create order: {}", request.getOrderId(), ex);
            throw ex;
        } finally {
            // Clean up MDC
            MDC.remove("orderId");
        }
    }
}
```

### Log Levels

- `ERROR` - Critical errors that need immediate attention
- `WARN` - Warning conditions (e.g., 4xx HTTP responses)
- `INFO` - Important business events (order created, payment received)
- `DEBUG` - Detailed diagnostic information (development only)
- `TRACE` - Very detailed debugging (rarely used)

**Production Recommendations:**
- Application code: `INFO`
- Spring Framework: `WARN`
- Third-party libraries: `WARN`
- Payment providers: `DEBUG` (for troubleshooting)

## Metrics & Monitoring

### Prometheus Endpoint

Metrics are exposed at:
```
http://localhost:4242/actuator/prometheus
```

### Built-in Metrics

Spring Boot Actuator automatically tracks:

**JVM Metrics:**
- `jvm.memory.used` - Memory usage
- `jvm.gc.pause` - Garbage collection pauses
- `jvm.threads.live` - Active thread count

**HTTP Metrics:**
- `http.server.requests` - Request count, duration, status
- `http.server.requests.percentile` - Response time percentiles (p50, p95, p99)

**Database Metrics:**
- `hikaricp.connections.active` - Active DB connections
- `hikaricp.connections.idle` - Idle DB connections

**Cache Metrics:**
- `cache.gets` - Cache get operations
- `cache.puts` - Cache put operations
- `cache.evictions` - Cache evictions

### Custom Business Metrics

The `MetricsService` provides application-specific metrics:

**Order Metrics:**
```java
@Autowired
private MetricsService metricsService;

public void createOrder(Order order) {
    // Record order created
    metricsService.recordOrderCreated(order.getStatus());
}
```

Available custom metrics:
- `orders.created{status}` - Orders created by status
- `payment.transactions{provider,status}` - Payment transactions
- `payment.amount` - Payment amounts (distribution summary)
- `payment.duration{provider,operation}` - Payment API call duration
- `auth.attempts{result}` - Authentication attempts
- `api.errors{endpoint,error_type}` - API errors
- `cache.access{cache,result}` - Cache hit/miss ratio
- `email.sent{type,result}` - Email sending results
- `webhook.received{provider,verified}` - Webhook events

### Grafana Dashboard

Example Prometheus queries:

**Request Rate:**
```promql
rate(http_server_requests_seconds_count[5m])
```

**Error Rate:**
```promql
rate(http_server_requests_seconds_count{status=~"5.."}[5m])
```

**Response Time (p95):**
```promql
histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))
```

**Payment Success Rate:**
```promql
rate(payment_transactions_total{status="PAID"}[5m]) / rate(payment_transactions_total[5m])
```

## Health Checks

### Actuator Endpoints

Health check endpoints:

**Liveness Probe** (Kubernetes-ready):
```
GET /actuator/health/liveness
```

**Readiness Probe** (Kubernetes-ready):
```
GET /actuator/health/readiness
```

**Overall Health:**
```
GET /actuator/health
```

Response example:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    },
    "redis": {
      "status": "UP",
      "details": {
        "version": "7.0.0"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 500000000000,
        "free": 250000000000,
        "threshold": 10485760
      }
    },
    "paymentProvider": {
      "status": "UP",
      "details": {
        "vnpay": "available",
        "momo": "available",
        "zalopay": "available"
      }
    }
  }
}
```

### Custom Health Indicators

The application includes custom health checks:

**PaymentProviderHealthIndicator:**
- Checks payment provider availability
- Can be extended to ping actual provider endpoints

Add more custom health indicators:

```java
@Component
public class CustomHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        // Check custom component
        boolean isHealthy = checkComponent();
        
        if (isHealthy) {
            return Health.up()
                .withDetail("component", "available")
                .build();
        } else {
            return Health.down()
                .withDetail("component", "unavailable")
                .withDetail("reason", "Connection timeout")
                .build();
        }
    }
}
```

## Request Tracing

### Correlation IDs

Every HTTP request gets a unique `requestId` (correlation ID):

**Request:**
```http
GET /api/v1/orders/123
X-Request-ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

**Response:**
```http
HTTP/1.1 200 OK
X-Request-ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

The `requestId` is automatically:
- Generated if not provided
- Added to response headers
- Included in all log entries via MDC
- Useful for distributed tracing

### Request/Response Logging

The `RequestLoggingFilter` automatically logs:

**Incoming Request:**
```
INFO  Incoming request: POST /api/v1/orders
```

**Successful Response:**
```
INFO  Request completed: POST /api/v1/orders - Status: 200 - Duration: 45ms
```

**Error Response:**
```
ERROR Request completed: POST /api/v1/orders - Status: 500 - Duration: 123ms
```

### Distributed Tracing (Future Enhancement)

To enable distributed tracing with Zipkin/Jaeger:

1. Add dependencies (already included):
   ```gradle
   implementation 'io.micrometer:micrometer-tracing-bridge-brave'
   ```

2. Configure in `application.yml`:
   ```yaml
   management:
     tracing:
       sampling:
         probability: 0.1  # Sample 10% of requests
     zipkin:
       tracing:
         endpoint: http://localhost:9411/api/v2/spans
   ```

3. Run Zipkin:
   ```bash
   docker run -d -p 9411:9411 openzipkin/zipkin
   ```

## Integration with Monitoring Tools

### Prometheus + Grafana

**1. Prometheus Configuration (`prometheus.yml`):**
```yaml
scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:4242']
```

**2. Start Prometheus:**
```bash
docker run -d -p 9090:9090 \
  -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
```

**3. Start Grafana:**
```bash
docker run -d -p 3000:3000 grafana/grafana
```

**4. Import Spring Boot Dashboard:**
- Dashboard ID: 4701 (JVM Micrometer)
- Dashboard ID: 12900 (Spring Boot Statistics)

### ELK Stack (Elasticsearch, Logstash, Kibana)

**1. Configure Filebeat to ship logs:**
```yaml
filebeat.inputs:
  - type: log
    enabled: true
    paths:
      - /var/log/app/spring.log
    json.keys_under_root: true
    json.add_error_key: true

output.elasticsearch:
  hosts: ["localhost:9200"]
```

**2. Create Kibana index pattern:**
- Index pattern: `filebeat-*`
- Time field: `@timestamp`

**3. Query logs in Kibana:**
```
application: "vinyl-ecommerce-backend" AND level: "ERROR"
orderId: "ORD-123"
requestId: "a1b2c3d4-*"
```

### AWS CloudWatch

Configure CloudWatch agent to collect logs:

```json
{
  "logs": {
    "logs_collected": {
      "files": {
        "collect_list": [
          {
            "file_path": "/var/log/app/spring.log",
            "log_group_name": "/aws/ec2/vinyl-ecommerce",
            "log_stream_name": "{instance_id}"
          }
        ]
      }
    }
  }
}
```

## Alerting

### Prometheus Alerting Rules

Create `alerts.yml`:

```yaml
groups:
  - name: application_alerts
    interval: 30s
    rules:
      - alert: HighErrorRate
        expr: rate(http_server_requests_seconds_count{status=~"5.."}[5m]) > 0.05
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "High error rate detected"
          description: "Error rate is {{ $value }} errors/sec"

      - alert: HighResponseTime
        expr: histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m])) > 1
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High response time detected"
          description: "P95 response time is {{ $value }}s"

      - alert: PaymentProviderDown
        expr: up{job="spring-boot-app"} == 0
        for: 1m
        labels:
          severity: critical
        annotations:
          summary: "Application is down"
```

### Email/Slack Notifications

Configure Alertmanager:

```yaml
route:
  receiver: 'team-email'
  group_by: ['alertname']
  group_wait: 10s
  group_interval: 5m
  repeat_interval: 3h

receivers:
  - name: 'team-email'
    email_configs:
      - to: 'team@vinylecommerce.com'
        from: 'alerts@vinylecommerce.com'
        smarthost: 'smtp.gmail.com:587'
  
  - name: 'slack-notifications'
    slack_configs:
      - api_url: 'https://hooks.slack.com/services/XXX/YYY/ZZZ'
        channel: '#alerts'
```

## Best Practices

### Logging

✅ **DO:**
- Use structured logging (JSON) in production
- Include correlation IDs in all logs
- Log business events at INFO level
- Log errors with full stack traces
- Use appropriate log levels
- Clean up MDC after use

❌ **DON'T:**
- Log sensitive data (passwords, tokens, credit cards)
- Log at DEBUG level in production
- Log inside tight loops
- Use `System.out.println()`

### Metrics

✅ **DO:**
- Use tags for dimensionality
- Record both successes and failures
- Use histograms for latency measurements
- Keep metric names consistent
- Document custom metrics

❌ **DON'T:**
- Create metrics with high cardinality tags (e.g., user IDs)
- Record every single value (use sampling for high-volume metrics)
- Mix units (always use base units: seconds, bytes)

### Health Checks

✅ **DO:**
- Keep health checks lightweight
- Check actual dependencies (DB, Redis, external APIs)
- Use readiness vs liveness probes correctly
- Return detailed information in health responses

❌ **DON'T:**
- Make health checks depend on non-critical services
- Include slow operations in health checks
- Return sensitive information in health endpoints

## Troubleshooting

### No Metrics Appearing

**Check Actuator endpoints:**
```bash
curl http://localhost:4242/actuator/health
curl http://localhost:4242/actuator/metrics
curl http://localhost:4242/actuator/prometheus
```

**Verify configuration:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"  # For development only!
```

### JSON Logs Not Working

**Check active profile:**
```bash
# Should be "prod" for JSON logs
curl http://localhost:4242/actuator/env | grep activeProfiles
```

**Verify logback-spring.xml:**
- Check `<springProfile name="prod">` block
- Ensure logstash-logback-encoder dependency is present

### High Memory Usage

**Check heap usage:**
```bash
curl http://localhost:4242/actuator/metrics/jvm.memory.used
```

**Enable GC logging:**
```bash
java -Xlog:gc* -jar app.jar
```

## Additional Resources

- [Spring Boot Actuator Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Documentation](https://micrometer.io/docs)
- [Prometheus Best Practices](https://prometheus.io/docs/practices/naming/)
- [Logback Configuration](https://logback.qos.ch/manual/configuration.html)
- [Structured Logging Best Practices](https://www.thoughtworks.com/insights/blog/structured-logging)
