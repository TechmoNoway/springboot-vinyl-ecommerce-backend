package com.trikynguci.springbootvinylecommercebackend.controller;

import com.trikynguci.springbootvinylecommercebackend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemAPI {

     private final OrderItemService orderItemService;

     @GetMapping("")
     public ResponseEntity<?> doGetAllOrderItems(){
         HashMap<String, Object> result = new HashMap<>();

         try {
             result.put("success", true);
             result.put("message", "Success to call API getAllOrderItems");
             result.put("data", orderItemService.getAllOrderItems());
         } catch (Exception e) {
             result.put("success", false);
             result.put("message", "Fail to call API getAllOrderItems");
             result.put("data", null);
             log.error("Error: ", e);
         }

         return ResponseEntity.ok(result);
     }
}
