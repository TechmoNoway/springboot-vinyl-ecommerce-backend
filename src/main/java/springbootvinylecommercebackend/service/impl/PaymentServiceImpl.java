package springbootvinylecommercebackend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springbootvinylecommercebackend.dto.response.VietQRGenerateResponse;
import springbootvinylecommercebackend.service.PaymentService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${vietqr.clientId}")
    private String clientId;

    @Value("${vietqr.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public VietQRGenerateResponse generateVietQR(String amount) throws JsonProcessingException {
        String url = "https://api.vietqr.io/v2/generate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id", clientId);
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");


//        String requestBody = "{"
//                + "\"accountNo\": \"5907281009073\","
//                + "\"accountName\": \"Vinyl Record\","
//                + "\"acqId\": \"970405\","
//                + "\"addInfo\": \"Send\","
//                + "\"amount\":" + amount + ","
//                + "\"template\": \"compact2\""
//                + "}";

        String requestBody = "{"
                + "\"accountNo\": \"5907281009073\","
                + "\"accountName\": \"Vinyl Record\","
                + "\"acqId\": \"970405\","
                + "\"addInfo\": \"Send\","
                + "\"amount\":" + "1000" + ","
                + "\"template\": \"compact2\""
                + "}";

        HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(Objects.requireNonNull(response.getBody()).toString(), VietQRGenerateResponse.class);
    }
}
