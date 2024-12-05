package springbootvinylecommercebackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import springbootvinylecommercebackend.config.JwtService;
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.dto.request.AuthenticationRequest;
import springbootvinylecommercebackend.dto.response.AuthenticationResponse;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import springbootvinylecommercebackend.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) throws Exception {
		User user = User.builder()
				.id(request.getId())
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.address(request.getAddress())
				.birthday(request.getBirthday())
				.fullname(request.getFullname())
				.avatar(request.getAvatar())
				.phone(request.getPhone())
				.build();
				
		userMapper.saveUserRegister(user);				
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
		authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(
				request.getUsername(),
				request.getPassword()							
			)				
		);
		
		User user = userMapper.getUserByUsername(request.getUsername());		
		String jwtToken = jwtService.generateToken(user);		
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}

}
