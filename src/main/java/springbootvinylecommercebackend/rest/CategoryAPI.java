package springbootvinylecommercebackend.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootvinylecommercebackend.service.CategoryService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryAPI {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> doGetAllCategories() {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("success", true);
            result.put("message", "Successfully call doGetAllCategories");
            result.put("data", categoryService.getAllCategories());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Failed to call doGetAllCategories");
            result.put("data", null);
            log.error("Error: ", e);
        }

        return ResponseEntity.ok(result);
    }

}
