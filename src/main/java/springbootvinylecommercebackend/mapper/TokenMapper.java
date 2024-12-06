package springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootvinylecommercebackend.model.Token;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TokenMapper {
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByAccessToken(String accessToken);
    void saveToken(Token token);
    Token getTokenById(Long id);
    void updateToken(Token token);
}
