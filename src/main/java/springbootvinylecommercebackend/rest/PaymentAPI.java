package springbootvinylecommercebackend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springbootvinylecommercebackend.dto.response.VietQRGenerateResponse;
import springbootvinylecommercebackend.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentAPI {

    @Value("${vietqr.clientId}")
    private String clientId;

    @Value("${vietqr.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final PaymentService paymentService;

    @PostMapping("/generate-vietqr")
    public ResponseEntity<?> generateVietQR(@RequestParam("amount") String amount) {

        Map<String, Object> result = new HashMap<>();
        try {
            VietQRGenerateResponse vietQRGenerateResponse = paymentService.generateVietQR(amount);
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
