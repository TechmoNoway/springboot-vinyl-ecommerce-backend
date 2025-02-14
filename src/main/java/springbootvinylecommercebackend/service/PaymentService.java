package springbootvinylecommercebackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import springbootvinylecommercebackend.dto.response.VietQRGenerateResponse;

public interface PaymentService {
    VietQRGenerateResponse generateVietQR(String amount) throws JsonProcessingException;
}
