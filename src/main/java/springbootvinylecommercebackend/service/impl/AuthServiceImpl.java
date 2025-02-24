package springbootvinylecommercebackend.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.dto.response.RegisterResponse;
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.AuthService;
import springbootvinylecommercebackend.service.EmailService;
import springbootvinylecommercebackend.service.JwtService;
import springbootvinylecommercebackend.service.TokenService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public RegisterResponse register(String email) throws MessagingException {
        if (userMapper.existsByEmail(email)) {
            System.out.println("Email already exists");
        } else {
            String tempPassword = emailService.sendRegistrationEmail(email);
            System.out.println(email);
            User user = User.builder()
                    .email(email)
                    .fullname(email.substring(0, email.indexOf("@")))
                    .password(passwordEncoder.encode(tempPassword))
                    .roleId(2L)
                    .build();

            userMapper.saveUser(user);

            String jwtToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

//            tokenService.saveToken(user.getId(), jwtToken);


            return RegisterResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
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
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userID(user.getId())
                .build();
    }
}
