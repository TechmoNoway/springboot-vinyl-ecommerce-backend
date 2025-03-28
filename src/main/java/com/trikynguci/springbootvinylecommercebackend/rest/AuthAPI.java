package com.trikynguci.springbootvinylecommercebackend.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trikynguci.springbootvinylecommercebackend.dto.request.LoginRequest;
import com.trikynguci.springbootvinylecommercebackend.service.AuthService;
import com.trikynguci.springbootvinylecommercebackend.service.EmailService;
import com.trikynguci.springbootvinylecommercebackend.service.TokenService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthAPI {

    private final AuthService authService;

    private final TokenService tokenService;

    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest){
        Map<String, Object> result = new HashMap<>();

        try {

            result.put("success", true);
            result.put("message", "Success to call api doLogin");
            result.put("data", authService.login(loginRequest));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Failed to call api doLogin");
            result.put("data", null);
            log.error("Error: ", e);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestParam("email") String email){
        Map<String, Object> result = new HashMap<>();

        try {
            authService.register(email);
            result.put("success", true);
            result.put("message", "Success to call api doRegister");
            result.put("data", null);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Failed to call api doRegister");
            result.put("data", null);
            log.error("Error: ", e);
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
