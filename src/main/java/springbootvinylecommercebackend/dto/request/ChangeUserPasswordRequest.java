package springbootvinylecommercebackend.dto.request;

import lombok.Data;

@Data
public class ChangeUserPasswordRequest {
    private Long userID;
    private String currentPassword;
    private String newPassword;
}

