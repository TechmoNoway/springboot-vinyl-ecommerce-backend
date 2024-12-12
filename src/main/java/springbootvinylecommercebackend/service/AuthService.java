package springbootvinylecommercebackend.service;

import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
