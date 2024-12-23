package springbootvinylecommercebackend.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.request.RegisterRequest;
import springbootvinylecommercebackend.service.AuthService;
import springbootvinylecommercebackend.service.TokenService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthAPI {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest){
        Map<String, Object> result = new HashMap<>();

        try {
            authService.login(loginRequest);
            result.put("success", true);
            result.put("message", "Success to call api doLogin");
            result.put("data", null);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            result.put("data", null);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody RegisterRequest registerRequest){
        Map<String, Object> result = new HashMap<>();

        try {
            authService.register(registerRequest);
            result.put("success", true);
            result.put("message", "Success to call api doLogin");
            result.put("data", null);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            result.put("data", null);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        tokenService.refreshToken(request, response);
    }
}
