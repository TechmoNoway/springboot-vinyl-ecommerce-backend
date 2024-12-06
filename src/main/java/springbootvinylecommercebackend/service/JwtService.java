package springbootvinylecommercebackend.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String accessToken);

    <T> T extractClaim(String accessToken, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration);

    boolean isTokenValid(String accessToken, UserDetails userDetails);

    boolean isTokenExpired(String accessToken);

    Date extractExpiration(String accessToken);

    Claims extractAllClaims(String accessToken);

    Key getSignInKey();
}
