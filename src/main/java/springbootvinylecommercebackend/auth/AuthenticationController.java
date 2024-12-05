package springbootvinylecommercebackend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.dto.request.AuthenticationRequest;
import springbootvinylecommercebackend.dto.response.AuthenticationResponse;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws Exception{
		 return ResponseEntity.ok(authenticationService.register(request));
	}
	
	@PostMapping("/user/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception{
		 return ResponseEntity.ok(authenticationService.authenticate(request));
		
	}
}
