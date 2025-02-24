package springbootvinylecommercebackend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String accessToken;
    private String refreshToken;
    private Long userID;
}
