package com.trikynguci.springbootvinylecommercebackend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {
     void saveToken(Long userId, String token);

     void revokeAllUserTokens(Long userId);

     void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
