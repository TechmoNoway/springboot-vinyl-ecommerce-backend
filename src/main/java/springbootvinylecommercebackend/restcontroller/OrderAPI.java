package springbootvinylecommercebackend.restcontroller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.model.Order;
import springbootvinylecommercebackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("http://localhost:3000")
@Slf4j
@RequestMapping("/api/order")
public class OrderAPI {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/getAllOrders")
	ResponseEntity<?> doGetAllOrders(){
		
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getAllOrders");
			result.put("data", orderService.getAllOrders());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getAllOrders");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
		
		
	}
	
	@PostMapping("/saveOrder")
	ResponseEntity<?> doSaveOrder(@RequestBody Order order){
		
		HashMap<String, Object> result = new HashMap<>();

		try {
			orderService.saveOrder(order);
			orderService.sendOrderSuccessMail(order.getEmail());
			result.put("success", true);
			result.put("message", "Success to call API getAllOrders");
			result.put("data", order);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getAllOrders");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
		
		
		
	}
	
	
}
