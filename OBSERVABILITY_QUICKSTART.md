# Quick Start - Observability

This guide shows you how to quickly start monitoring your Spring Boot Vinyl E-Commerce Backend.

## 🚀 Quick Setup (5 minutes)

### 1. Verify Actuator is Running

Start your application and check the health endpoint:

```bash
# Check application health
curl http://localhost:4242/actuator/health

# Expected response:
# {"status":"UP"}
```

### 2. View Available Metrics

```bash
# List all available metrics
curl http://localhost:4242/actuator/metrics

# View specific metric (JVM memory)
curl http://localhost:4242/actuator/metrics/jvm.memory.used
```

### 3. Check Prometheus Endpoint

```bash
# View Prometheus-formatted metrics
curl http://localhost:4242/actuator/prometheus
```

## 📊 Start Prometheus + Grafana (Docker)

### Option A: Docker Compose (Recommended)

Create `monitoring/docker-compose.yml`:

```yaml
version: '3.8'

services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus-data:/prometheus
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
    restart: unless-stopped

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
      - GF_USERS_ALLOW_SIGN_UP=false
    volumes:
      - grafana-data:/var/lib/grafana
    restart: unless-stopped
    depends_on:
      - prometheus

volumes:
  prometheus-data:
  grafana-data:
```

Create `monitoring/prometheus.yml`:

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:4242']  # Windows/Mac
        # For Linux, use: ['172.17.0.1:4242']
    scrape_interval: 5s
```

Start monitoring stack:

```bash
cd monitoring
docker-compose up -d

# Check logs
docker-compose logs -f
```

### Access Dashboards

**Prometheus:**
- URL: http://localhost:9090
- Test query: `http_server_requests_seconds_count`

**Grafana:**
- URL: http://localhost:3000
- Username: `admin`
- Password: `admin`

### Import Spring Boot Dashboard in Grafana

1. Go to http://localhost:3000
2. Click **+** → **Import Dashboard**
3. Enter Dashboard ID: **4701** (JVM Micrometer)
4. Select **Prometheus** as data source
5. Click **Import**

Popular Spring Boot dashboards:
- **4701** - JVM (Micrometer)
- **12900** - Spring Boot Statistics
- **11378** - Spring Boot APM Dashboard

## 📝 View Logs

### Development Mode (Console)

Logs automatically go to console with color coding:

```bash
# Start application
./gradlew bootRun

# Or
java -jar build/libs/app.jar
```

### Production Mode (JSON Logs)

Enable production profile:

```bash
# Set profile
export SPRING_PROFILES_ACTIVE=prod

# Run application
java -jar build/libs/app.jar

# Logs will be in JSON format
tail -f /tmp/spring.log | jq .
```

### View Logs in Docker

```bash
# Follow container logs
docker logs -f <container-name>

# Filter ERROR logs
docker logs <container-name> 2>&1 | grep '"level":"ERROR"'

# Pretty print JSON logs
docker logs <container-name> 2>&1 | jq .
```

## 🔍 Testing Custom Metrics

### 1. Create Some Activity

```bash
# Trigger authentication (will record auth.attempts metric)
curl -X POST http://localhost:4242/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test123"}'

# Browse products (will record HTTP metrics)
curl http://localhost:4242/api/v1/products

# Create order (will record orders.created metric)
curl -X POST http://localhost:4242/api/v1/orders \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"items":[{"productId":1,"quantity":1}]}'
```

### 2. View Metrics in Prometheus

Go to http://localhost:9090 and try these queries:

**Request Rate:**
```promql
rate(http_server_requests_seconds_count[1m])
```

**Error Rate:**
```promql
rate(http_server_requests_seconds_count{status=~"5.."}[1m])
```

**Response Time P95:**
```promql
histogram_quantile(0.95, rate(http_server_requests_seconds_bucket[5m]))
```

**Orders Created:**
```promql
orders_created_total
```

**Authentication Failures:**
```promql
auth_attempts_total{result="failure"}
```

## 📈 Quick Grafana Panels

### 1. Create Dashboard

1. Go to Grafana → **+** → **Dashboard**
2. Click **Add panel**

### 2. Add Request Rate Panel

**Query:**
```promql
sum(rate(http_server_requests_seconds_count[1m])) by (uri)
```

**Visualization:** Time series
**Panel title:** Request Rate by Endpoint

### 3. Add Error Panel

**Query:**
```promql
sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m]))
```

**Visualization:** Stat
**Panel title:** Error Rate
**Color:** Red if > 0

### 4. Add Response Time Panel

**Query:**
```promql
histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[5m])) by (le))
```

**Visualization:** Time series
**Panel title:** Response Time P95
**Unit:** seconds (s)

## 🚨 Quick Alerts

Create `monitoring/alerts.yml`:

```yaml
groups:
  - name: application_alerts
    interval: 30s
    rules:
      - alert: HighErrorRate
        expr: rate(http_server_requests_seconds_count{status=~"5.."}[5m]) > 0.05
        for: 2m
        labels:
          severity: critical
        annotations:
          summary: "High error rate detected"
          description: "Error rate is {{ $value | humanize }} errors/sec"

      - alert: HighMemoryUsage
        expr: jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"} > 0.9
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High memory usage detected"
          description: "Heap usage is {{ $value | humanizePercentage }}"
```

Add to `prometheus.yml`:

```yaml
rule_files:
  - 'alerts.yml'

alerting:
  alertmanagers:
    - static_configs:
        - targets: ['localhost:9093']  # If using Alertmanager
```

## 🔧 Troubleshooting

### No Metrics Showing Up

**Check Actuator endpoints:**
```bash
curl http://localhost:4242/actuator/health
curl http://localhost:4242/actuator/prometheus
```

**If 404:** Verify `management.endpoints.web.exposure.include` in `application.yml`

### Prometheus Can't Scrape

**Check connectivity:**
```bash
# From Prometheus container
docker exec -it prometheus wget -O- http://host.docker.internal:4242/actuator/prometheus
```

**Common issues:**
- Firewall blocking port 4242
- Application not running
- Wrong target in `prometheus.yml`

### JSON Logs Not Working

**Check active profile:**
```bash
curl http://localhost:4242/actuator/env | jq '.propertySources[] | select(.name=="activeProfiles")'
```

**Set profile correctly:**
```bash
export SPRING_PROFILES_ACTIVE=prod
# or
java -Dspring.profiles.active=prod -jar app.jar
```

## 📚 Next Steps

1. **Read full documentation:** [OBSERVABILITY.md](./OBSERVABILITY.md)
2. **Set up alerting:** Configure Alertmanager for notifications
3. **Add distributed tracing:** Enable Zipkin/Jaeger integration
4. **Create runbooks:** Document incident response procedures
5. **Set up log aggregation:** Configure ELK stack or cloud logging

## 🎯 Key Endpoints

| Endpoint | Purpose | Public |
|----------|---------|--------|
| `/actuator/health` | Overall health status | ✅ Yes |
| `/actuator/health/liveness` | Liveness probe (K8s) | ✅ Yes |
| `/actuator/health/readiness` | Readiness probe (K8s) | ✅ Yes |
| `/actuator/prometheus` | Prometheus metrics | ✅ Yes |
| `/actuator/metrics` | Metrics list | ✅ Yes |
| `/actuator/loggers` | Log level management | 🔒 Auth required |
| `/actuator/env` | Environment info | 🔒 Auth required |

## 💡 Pro Tips

1. **Use correlation IDs:** Always include `X-Request-ID` in API requests
2. **Monitor business metrics:** Track orders, payments, not just technical metrics
3. **Set up alerts early:** Don't wait for production issues
4. **Test failure scenarios:** Verify alerts trigger correctly
5. **Regular dashboard reviews:** Make observability part of daily routine

---

**Need help?** See [OBSERVABILITY.md](./OBSERVABILITY.md) for detailed documentation.
