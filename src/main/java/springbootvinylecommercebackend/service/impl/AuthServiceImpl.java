package springbootvinylecommercebackend.service.impl;

import jakarta.mail.MessagingException;
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
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.AuthService;
import springbootvinylecommercebackend.service.EmailService;
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
    private final EmailService emailService;

    @Override
    public void register(RegisterRequest request) throws MessagingException {

        if (userMapper.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String tempPassword = emailService.sendRegistrationEmail(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .fullname(request.getEmail().substring(0, request.getEmail().indexOf("@")))
                .password(passwordEncoder.encode(tempPassword))
                .build();

        userMapper.saveUser(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenService.saveToken(user.getId(), jwtToken);
        RegisterResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserConvert.toDto(user))
                .build();
    }

    @Override
    public void login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userMapper.getUserByEmail(request.getEmail()).orElseThrow();
        if (!user.isEnabled()) {
            throw new RuntimeException();
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user.getId());
        tokenService.saveToken(user.getId(), jwtToken);
        LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(UserConvert.toDto(user))
                .build();
    }
}
