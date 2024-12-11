package springbootvinylecommercebackend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.dto.response.RegisterResponse;

import java.io.IOException;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse authenticate(LoginRequest request);
    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

}
