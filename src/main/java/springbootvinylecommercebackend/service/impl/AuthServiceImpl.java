package springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApplicationEventPublisher publisher;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

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
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userMapper.getUserByUsername(request.getUsername()).orElseThrow();
        if (!user.isEnabled()) {
            throw new RuntimeException();
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user.getId());
        tokenService.saveToken(user.getId(), jwtToken);
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserConvert.toDto(user))
                .build();
    }
}
