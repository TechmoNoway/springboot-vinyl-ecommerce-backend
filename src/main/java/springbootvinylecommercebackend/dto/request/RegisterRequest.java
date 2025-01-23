package springbootvinylecommercebackend.dto.request;

import java.sql.Date;

import springbootvinylecommercebackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RegisterRequest {	
	private Long id;
	private String email;
	private String password;
	private String avatar;
	private String phone;
	private Date birthday;
	private String fullname;
	private String address;
	private Role role;
	
}
