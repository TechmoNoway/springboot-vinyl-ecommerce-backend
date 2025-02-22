package springbootvinylecommercebackend.service;

import jakarta.mail.MessagingException;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(String email) throws MessagingException;
    LoginResponse login(LoginRequest request);
}
