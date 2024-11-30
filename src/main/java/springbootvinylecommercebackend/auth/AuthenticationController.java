package springbootvinylecommercebackend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.model.AuthenticationRequest;
import springbootvinylecommercebackend.model.AuthenticationResponse;
import springbootvinylecommercebackend.model.RegisterRequest;
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
