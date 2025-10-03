package com.trikynguci.springbootvinylecommercebackend.controller;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trikynguci.springbootvinylecommercebackend.dto.request.OrderRequest;
import com.trikynguci.springbootvinylecommercebackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderAPI {

	private final OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<?> doGetAllOrders(){
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getAllOrders");
			result.put("data", orderService.getAllOrders());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getAllOrders");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/place-order")
	public ResponseEntity<?> doSaveOrder(@RequestBody OrderRequest orderRequest){
		var order = orderService.placeOrder(orderRequest);
		// send email asynchronously
		orderService.sendOrderSuccessMail(orderRequest.getEmail());
		HashMap<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("message", "Success to call API place order");
		result.put("data", order);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doGetOrderById(@PathVariable("id") String id){
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getOrderById");
			result.put("data", orderService.getOrderById(id));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getOrderById");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> doGetOrdersByUserId(@PathVariable Long userId){
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getOrderById");
			result.put("data", orderService.getOrdersByUserId(userId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getOrderById");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}
	
	
}
