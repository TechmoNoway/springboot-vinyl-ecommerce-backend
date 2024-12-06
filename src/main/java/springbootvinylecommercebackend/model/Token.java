package springbootvinylecommercebackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootvinylecommercebackend.enums.TokenType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    public Long id;
    public String accessToken;
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    public Long userId;
}