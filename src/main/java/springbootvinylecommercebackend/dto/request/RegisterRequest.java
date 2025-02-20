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
	private String email;
}
