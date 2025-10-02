package com.trikynguci.springbootvinylecommercebackend.rest;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trikynguci.springbootvinylecommercebackend.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductAPI {

    private final ProductService ProductService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "stockStatus", required = false) String stockStatus,
            @RequestParam(value = "studioName", required = false) String studioName,
            @RequestParam(value = "manufactureYear", required = false) String manufactureYear,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sortType", required = false) String sortType,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
    ) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Success to call API getAllProducts");
            result.put("data", ProductService.getAllProducts(
                title, category, platform, stockStatus, studioName, manufactureYear, status, sortType, limit, page
            ));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Fail to call API getAllProducts");
            result.put("data", null);
            log.error("Error", e);
        }
        return ResponseEntity.ok(result);
    }

}
