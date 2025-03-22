package com.trikynguci.springbootvinylecommercebackend.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import com.trikynguci.springbootvinylecommercebackend.mapper.TokenMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Token;

@Service
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {

    private final TokenMapper tokenMapper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token storedToken = tokenMapper.findByAccessToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenMapper.saveToken(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
