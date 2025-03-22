package com.trikynguci.springbootvinylecommercebackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
}
