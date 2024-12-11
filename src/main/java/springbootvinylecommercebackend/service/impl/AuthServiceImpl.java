package springbootvinylecommercebackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.dto.response.RegisterResponse;
import springbootvinylecommercebackend.event.RegistrationEvent;
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.AuthService;
import springbootvinylecommercebackend.service.JwtService;
import springbootvinylecommercebackend.service.TokenService;
import springbootvinylecommercebackend.util.UserConvert;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher publisher;
    private final TokenService tokenService;
    private final UserConvert userConvert;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullname(request.getFullname())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .birthday(request.getBirthday())
                .build();

        userMapper.saveUser(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RegistrationEvent registrationEvent = new RegistrationEvent(user, jwtToken);
        publisher.publishEvent(registrationEvent);

        tokenService.saveToken(user.getId(), jwtToken);
        return RegisterResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserConvert.toDto(user))
                .build();
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        return null;
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
