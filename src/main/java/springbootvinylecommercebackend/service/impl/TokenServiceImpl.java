package springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.enums.TokenType;
import springbootvinylecommercebackend.mapper.TokenMapper;
import springbootvinylecommercebackend.model.Token;
import springbootvinylecommercebackend.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenMapper tokenMapper;

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
}
