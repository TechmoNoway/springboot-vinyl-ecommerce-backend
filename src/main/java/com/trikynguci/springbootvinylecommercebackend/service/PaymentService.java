package com.trikynguci.springbootvinylecommercebackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trikynguci.springbootvinylecommercebackend.dto.response.VietQRGenerateResponse;

public interface PaymentService {
    VietQRGenerateResponse generateVietQR(String amount) throws JsonProcessingException;
}
