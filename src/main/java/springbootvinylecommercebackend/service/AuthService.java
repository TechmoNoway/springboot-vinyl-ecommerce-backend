package springbootvinylecommercebackend.service;

import jakarta.mail.MessagingException;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.request.RegisterRequest;

public interface AuthService {
    void register(String email) throws MessagingException;
    void login(LoginRequest request);
}
