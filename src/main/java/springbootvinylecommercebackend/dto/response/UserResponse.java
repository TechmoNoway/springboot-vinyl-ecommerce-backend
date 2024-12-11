package springbootvinylecommercebackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.Instant;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String phone;
    private String gender;
    private Date birthday;
    private String fullname;
    private String address;
    private Long roleId;
    private Instant createdAt;
    private Instant updatedAt;
}