package springbootvinylecommercebackend.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootvinylecommercebackend.dto.request.LoginRequest;
import springbootvinylecommercebackend.dto.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthAPI {


    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest){
        Map<String, Object> result = new HashMap<>();

        try {
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

}
