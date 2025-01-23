package springbootvinylecommercebackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.dto.response.LoginResponse;
import springbootvinylecommercebackend.enums.TokenType;
import springbootvinylecommercebackend.mapper.TokenMapper;
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.model.Token;
import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.JwtService;
import springbootvinylecommercebackend.service.TokenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenMapper tokenMapper;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public void saveToken(Long userId, String jwtToken) {
        Token token = Token.builder()
                .userId(userId)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenMapper.saveToken(token);
    }

    public void revokeAllUserTokens(Long userId) {
        List<Token> validUserTokens = tokenMapper.findAllValidTokenByUser(userId);
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        validUserTokens.forEach(tokenMapper::saveToken);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
//        extract email
        String email = jwtService.extractUsername(refreshToken);
        if (email != null) {
            User user = this.userMapper.getUserByEmail(email).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user.getId());
                tokenService.saveToken(user.getId(), accessToken);
                var authResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
