package com.trikynguci.springbootvinylecommercebackend.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class MomoProvider {

    @Value("${momo.endpoint:https://test-payment.momo.vn/v2/gateway/api/create})")
    private String momoEndpoint;

    @Value("${momo.partnerCode:PARTNER}")
    private String partnerCode;

    @Value("${momo.accessKey:ACCESS}")
    private String accessKey;

    @Value("${momo.secretKey:SECRET}")
    private String secretKey;

    private final RestTemplate rest = new RestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    public String buildPaymentUrl(String returnUrl, String orderId, Long amount) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("partnerCode", partnerCode);
            payload.put("accessKey", accessKey);
            payload.put("amount", String.valueOf(amount));
            payload.put("orderId", orderId);
            payload.put("orderInfo", "Payment for order " + orderId);
            payload.put("returnUrl", returnUrl);
            payload.put("notifyUrl", returnUrl);
            payload.put("requestId", orderId);
            payload.put("extraData", "");

            // build raw signature string in expected order
            String rawSignature = "accessKey=" + accessKey + "&amount=" + payload.get("amount") + "&extraData=&orderId=" + orderId + "&partnerCode=" + partnerCode + "&requestId=" + orderId;
            String signature = hmacSHA256(secretKey, rawSignature);
            payload.put("signature", signature);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(payload), headers);

            JsonNode resp = rest.postForObject(momoEndpoint, entity, JsonNode.class);
            if (resp != null && resp.has("payUrl")) {
                return resp.get("payUrl").asText();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean verifyCallback(Map<String, String> params) {
        try {
            // Momo provides signature - typically HMAC SHA256 over ordered fields
            String signature = params.get("signature");
            if (signature == null) return false;
            // Build raw data depending on provider implementation - here we attempt a common pattern
            String raw = "partnerCode=" + params.get("partnerCode") + "&orderId=" + params.get("orderId") + "&requestId=" + params.get("requestId") + "&amount=" + params.get("amount") + "&responseTime=" + params.getOrDefault("responseTime", "") + "&extraData=" + params.getOrDefault("extraData", "");
            String computed = hmacSHA256(secretKey, raw);
            return signature.equalsIgnoreCase(computed);
        } catch (Exception ex) {
            return false;
        }
    }

    private static String hmacSHA256(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] bytes = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) hash.append('0');
            hash.append(hex);
        }
        return hash.toString();
    }
}
