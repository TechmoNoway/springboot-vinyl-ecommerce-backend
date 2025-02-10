package springbootvinylecommercebackend.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springbootvinylecommercebackend.dto.response.VietQRGenerateResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentAPI {

    @Value("${vietqr.clientId}")
    private String clientId;

    @Value("${vietqr.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @GetMapping("/generate-vietqr")
    public ResponseEntity<?> generateVietQR(@RequestParam("amount") String amount) {
        String url = "https://api.vietqr.io/v2/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id", clientId);
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");


        String requestBody = "{"
                + "\"accountNo\": \"5907281009073\","
                + "\"accountName\": \"NGUYEN TRI KY\","
                + "\"acqId\": \"970405\","
                + "\"addInfo\": \"Send\","
                + "\"amount\":" + amount + ","
                + "\"template\": \"compact2\""
                + "}";


        HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> result = new HashMap<>();
        try {
        VietQRGenerateResponse vietQRGenerateResponse = objectMapper.readValue(Objects.requireNonNull(response.getBody()).toString(), VietQRGenerateResponse.class);
            result.put("success", true);
            result.put("message", "VietQR generated successfully");
            result.put("data", vietQRGenerateResponse);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "VietQR generated failed");
            result.put("data", null);
            e.printStackTrace();
        }


        return ResponseEntity.ok(result);
    }

}
