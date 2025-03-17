package springbootvinylecommercebackend.rest;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductAPI {

    private final ProductService ProductService;

    @GetMapping("/ready")
    public ResponseEntity<?> doGetReadyProducts() {
        HashMap<String, Object> result = new HashMap<>();

        try {
            result.put("success", true);
            result.put("message", "Success to call API getReadyProducts");
            result.put("data", ProductService.getReadyProducts());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Fail to call API getReadyProducts");
            result.put("data", null);
            log.error("Error: ", e);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/title")
    public ResponseEntity<?> doGetProductByTitle(@RequestParam("title") String title) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            result.put("success", true);
            result.put("message", "Success to call API doGetProductByTitle");
            result.put("data", ProductService.getProductByTitle(title));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Fail to call API doGetProductByTitle");
            result.put("data", null);
            log.error("Error: ", e);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<?> doSearchProductsByTitle(@RequestParam("title") String title) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            result.put("success", true);
            result.put("message", "Success to call API doSearchProductsByTitle");
            result.put("data", ProductService.searchProductsByTitle(title));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Fail to call API getSearchProductsByTitle");
            result.put("data", null);
            log.error("Error: ", e);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<?> doGetAllProductsFilteredAndSorted(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "stockStatus", required = false) String stockStatus,
            @RequestParam(value = "studioName", required = false) String studioName,
            @RequestParam(value = "manufactureYear", required = false) String manufactureYear,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sortType", required = false) String sortType
    ) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            result.put("success", true);
            result.put("message", "Success to call API get all products filtered and sorted");
            result.put("data", ProductService.getAllProductsFilteredAndSorted(title, category, platform, stockStatus, studioName, manufactureYear, status, sortType));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Fail to call API get all products filtered and sorted");
            result.put("data", null);
            log.error("Error", e);
        }

        return ResponseEntity.ok(result);
    }

}
