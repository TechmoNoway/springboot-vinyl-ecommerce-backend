package com.trikynguci.springbootvinylecommercebackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.trikynguci.springbootvinylecommercebackend.dto.response.LoginResponse;
import com.trikynguci.springbootvinylecommercebackend.mapper.TokenMapper;
import com.trikynguci.springbootvinylecommercebackend.mapper.UserMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Token;
import com.trikynguci.springbootvinylecommercebackend.model.User;
import com.trikynguci.springbootvinylecommercebackend.service.JwtService;
import com.trikynguci.springbootvinylecommercebackend.service.TokenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenMapper tokenMapper;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public void saveToken(Long userId, String jwtToken) {
        Token token = Token.builder()
                .userId(userId)
                .accessToken(jwtToken)
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
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.userMapper.getUserByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user.getId());

                Token token = Token.builder()
                        .userId(user.getId())
                        .accessToken(accessToken)
                        .expired(false)
                        .revoked(false)
                        .build();
                tokenMapper.saveToken(token);

                var authResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
