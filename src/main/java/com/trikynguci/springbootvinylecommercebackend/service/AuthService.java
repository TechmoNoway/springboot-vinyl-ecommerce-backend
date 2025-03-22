package com.trikynguci.springbootvinylecommercebackend.service;

import jakarta.mail.MessagingException;
import com.trikynguci.springbootvinylecommercebackend.dto.request.LoginRequest;
import com.trikynguci.springbootvinylecommercebackend.dto.response.LoginResponse;
import com.trikynguci.springbootvinylecommercebackend.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(String email) throws MessagingException;
    LoginResponse login(LoginRequest request);
}
