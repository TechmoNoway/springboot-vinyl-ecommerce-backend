# Credentials & Environment Setup Guide

## ⚠️ Security Notice

**NEVER commit real credentials to version control!**

This project uses environment variables for all sensitive configuration. The file `application-dev.yml` is gitignored and should contain your local development secrets.

## Quick Start

1. **Copy the environment template:**
   ```bash
   cp .env.example .env
   ```

2. **Fill in your actual credentials in `.env`**

3. **Set environment variables** (choose one method):

   ### Option A: Using .env file (Recommended for local development)
   - Install a .env loader for Spring Boot
   - Or manually export variables before running

   ### Option B: Export manually (Linux/Mac)
   ```bash
   export DB_USERNAME=root
   export DB_PASSWORD=your_password
   # ... etc
   ```

   ### Option C: Export manually (Windows PowerShell)
   ```powershell
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_password"
   # ... etc
   ```

   ### Option D: Use `application-dev.yml` (Local only, already gitignored)
   Create `src/main/resources/application-dev.yml` with your secrets.
   **This file is already in .gitignore and will NOT be committed.**

## Required Environment Variables

### 1. Database (MySQL)

```bash
DB_USERNAME=root
DB_PASSWORD=your_database_password
```

**Setup:**
- Install MySQL locally or use Docker:
  ```bash
  docker run --name mysql-dev -e MYSQL_ROOT_PASSWORD=your_password -p 3306:3306 -d mysql:8
  ```

### 2. JWT Authentication

```bash
JWT_SECRET_KEY=your_secure_random_key_here
JWT_EXPIRATION=86400000
JWT_REFRESH_TOKEN_EXPIRATION=604800000
```

**Generate a secure key:**
```bash
# Linux/Mac
openssl rand -base64 32

# Or use online generator (ONLY for development):
# https://generate-random.org/api-key-generator
```

### 3. Redis Cache

```bash
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_USERNAME=default
REDIS_PASSWORD=your_redis_password
```

**Setup:**
```bash
# Using Docker
docker run --name redis-dev -p 6379:6379 -d redis:alpine

# Or install locally
# Mac: brew install redis
# Ubuntu: sudo apt install redis-server
```

**Free Cloud Redis (Development):**
- Redis Cloud: https://redis.com/try-free/
- Upstash: https://upstash.com/

### 4. Email (Gmail SMTP)

```bash
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

**Get Gmail App Password:**
1. Go to https://myaccount.google.com/apppasswords
2. Select "Mail" and "Other"
3. Generate password
4. Use the 16-character password (remove spaces)

**Alternative email providers:**
- SendGrid: https://sendgrid.com/
- Mailgun: https://www.mailgun.com/
- AWS SES: https://aws.amazon.com/ses/

### 5. VietQR Payment Gateway

```bash
VIETQR_CLIENT_ID=your_client_id
VIETQR_API_KEY=your_api_key
```

**Get credentials:**
- Visit: https://www.vietqr.io/
- Sign up for API access

### 6. OAuth2 Providers

#### GitHub OAuth

```bash
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
```

**Setup:**
1. Go to https://github.com/settings/developers
2. Click "New OAuth App"
3. Set callback URL: `http://localhost:4242/login/oauth2/code/github`
4. Get Client ID and Secret

#### Google OAuth

```bash
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

**Setup:**
1. Go to https://console.cloud.google.com/apis/credentials
2. Create OAuth 2.0 Client ID
3. Set redirect URI: `http://localhost:4242/login/oauth2/code/google`
4. Get Client ID and Secret

### 7. Payment Providers

#### VNPay

```bash
VNPAY_URL=https://sandbox.vnpayment.vn/paygate/vpc.htm
VNPAY_RETURN_URL=http://localhost:4242/api/v1/payments/webhook/vnpay
VNPAY_TMN_CODE=your_vnpay_terminal_code
VNPAY_HASH_SECRET=your_vnpay_hash_secret
```

**Get sandbox credentials:**
- Visit: https://sandbox.vnpayment.vn/
- Register for merchant account
- Access Developer Portal

#### Momo

```bash
MOMO_ENDPOINT=https://test-payment.momo.vn/v2/gateway/api/create
MOMO_RETURN_URL=http://localhost:4242/api/v1/payments/webhook/momo
MOMO_PARTNER_CODE=your_partner_code
MOMO_ACCESS_KEY=your_access_key
MOMO_SECRET_KEY=your_secret_key
```

**Get sandbox credentials:**
- Visit: https://developers.momo.vn/
- Register for developer account
- Create test application

#### ZaloPay

```bash
ZALOPAY_ENDPOINT=https://sb-openapi.zalopay.vn/v2/create
ZALOPAY_RETURN_URL=http://localhost:4242/api/v1/payments/webhook/zalopay
ZALOPAY_APP_ID=your_app_id
ZALOPAY_KEY1=your_key1
ZALOPAY_KEY2=your_key2
```

**Get sandbox credentials:**
- Visit: https://docs.zalopay.vn/
- Register for sandbox access

## CI/CD Configuration

### GitHub Actions Secrets

Add these secrets to your GitHub repository:
1. Go to: Settings → Secrets and variables → Actions
2. Add each variable from `.env.example` as a repository secret

### Docker Compose

Use environment variables in `docker-compose.yml`:

```yaml
services:
  app:
    environment:
      - DB_USERNAME=${DB_USERNAME}
      - DB_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET_KEY=${JWT_SECRET_KEY}
      # ... etc
```

## Security Best Practices

### ✅ DO:
- Use strong, randomly generated secrets
- Rotate credentials regularly
- Use different credentials for dev/staging/production
- Store production secrets in secure vaults (AWS Secrets Manager, HashiCorp Vault, etc.)
- Enable 2FA on all service accounts
- Use read-only credentials where possible
- Audit access logs regularly

### ❌ DON'T:
- Commit secrets to git (check with `git log --all --full-history --source -- '*application-dev.yml'`)
- Share credentials via Slack/email
- Use production credentials in development
- Hardcode credentials in source code
- Store credentials in screenshots or documentation
- Use weak/default passwords

## Verifying No Secrets in Git History

```bash
# Check if any secrets were accidentally committed
git log --all --full-history --source -- '*application-dev.yml'
git log --all --full-history --source -- '*.env'

# Search for potential secrets in history
git log -p | grep -i "password\|secret\|key" | grep -v "password:" | head -20
```

## Emergency: Secret Leaked

If you accidentally commit secrets:

1. **Immediately rotate the compromised credentials**
2. **Remove from git history:**
   ```bash
   # Use BFG Repo-Cleaner
   bfg --delete-files application-dev.yml
   git reflog expire --expire=now --all
   git gc --prune=now --aggressive
   ```
3. **Force push (⚠️ only if you're sure):**
   ```bash
   git push origin --force --all
   ```
4. **Notify your team**

## Troubleshooting

### "Could not connect to database"
- Check `DB_USERNAME` and `DB_PASSWORD`
- Verify MySQL is running: `mysql -u root -p`
- Check connection string in `application.yml`

### "Invalid JWT secret"
- Ensure `JWT_SECRET_KEY` is at least 256 bits (32+ characters)
- Regenerate: `openssl rand -base64 32`

### "Redis connection refused"
- Check Redis is running: `redis-cli ping` (should return PONG)
- Verify `REDIS_HOST` and `REDIS_PORT`

### "Email sending failed"
- Verify Gmail App Password (not your regular password)
- Enable "Less secure app access" if using regular password (not recommended)
- Check firewall allows port 587

## Additional Resources

- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [OWASP Secrets Management](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html)
- [GitHub Security Best Practices](https://docs.github.com/en/code-security/getting-started/best-practices-for-preventing-data-leaks-in-your-organization)
