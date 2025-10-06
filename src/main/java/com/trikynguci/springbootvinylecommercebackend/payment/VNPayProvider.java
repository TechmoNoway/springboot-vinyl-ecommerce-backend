package com.trikynguci.springbootvinylecommercebackend.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class VNPayProvider {

    @Value("${vnpay.url:https://sandbox.vnpayment.vn/vpcpay.html}")
    private String vnpayUrl;

    @Value("${vnpay.returnUrl:http://localhost:8080/api/payments/webhook/vnpay}")
    private String returnUrl;

    @Value("${vnpay.tmnCode:TEST}")
    private String tmnCode;

    @Value("${vnpay.hashSecret:SECRET}")
    private String hashSecret;

    public String buildPaymentUrl(String clientReturnUrl, String orderId, Long amount) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("vnp_Version", "2.1.0");
            params.put("vnp_Command", "pay");
            params.put("vnp_TmnCode", tmnCode);
            params.put("vnp_Amount", String.valueOf(amount * 100)); // amount in cents
            params.put("vnp_CurrCode", "VND");
            params.put("vnp_TxnRef", orderId);
            params.put("vnp_OrderInfo", "Payment for order " + orderId);
            params.put("vnp_Locale", "vn");
            params.put("vnp_ReturnUrl", clientReturnUrl != null ? clientReturnUrl : returnUrl);
            params.put("vnp_CreateDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

            // sort params
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (String fieldName : fieldNames) {
                String value = params.get(fieldName);
                if (value != null && value.length() > 0) {
                    if (hashData.length() > 0) {
                        hashData.append('&');
                        query.append('&');
                    }
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                            .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                }
            }

            String secureHash = hmacSHA512(hashSecret, hashData.toString());
            String paymentUrl = vnpayUrl + "?" + query.toString() + "&vnp_SecureHash=" + secureHash;
            return paymentUrl;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean verifyCallback(Map<String, String> params) {
        try {
            String receivedHash = params.get("vnp_SecureHash");
            if (receivedHash == null) return false;
            Map<String, String> copy = new HashMap<>(params);
            copy.remove("vnp_SecureHash");
            copy.remove("vnp_SecureHashType");
            List<String> fieldNames = new ArrayList<>(copy.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            for (String fieldName : fieldNames) {
                String value = copy.get(fieldName);
                if (value != null && value.length() > 0) {
                    if (hashData.length() > 0) hashData.append('&');
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                }
            }
            String computed = hmacSHA512(hashSecret, hashData.toString());
            return receivedHash.equalsIgnoreCase(computed);
        } catch (Exception ex) {
            return false;
        }
    }

    private static String hmacSHA512(String key, String data) throws Exception {
        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        sha512_HMAC.init(secret_key);
        byte[] bytes = sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) hash.append('0');
            hash.append(hex);
        }
        return hash.toString();
    }
}
