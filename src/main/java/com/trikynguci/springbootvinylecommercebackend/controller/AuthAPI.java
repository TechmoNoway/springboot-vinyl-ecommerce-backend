package com.trikynguci.springbootvinylecommercebackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import com.trikynguci.springbootvinylecommercebackend.dto.request.LoginRequest;
import com.trikynguci.springbootvinylecommercebackend.service.AuthService;
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

    @PostMapping("/google-login")
    public ResponseEntity<?> doGoogleLogin(@AuthenticationPrincipal OAuth2User principal){
        Map<String, Object> result = new HashMap<>();
        try {
            String email = (String) principal.getAttribute("email");
            String fullname = (String) principal.getAttribute("name");
            String avatarUrl = (String) principal.getAttribute("picture");
            result.put("success", true);
            result.put("message", "Success to call api doGoogleLogin");
            result.put("data", authService.loginWithGoogle(email, fullname, avatarUrl));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Failed to call api doGoogleLogin");
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
