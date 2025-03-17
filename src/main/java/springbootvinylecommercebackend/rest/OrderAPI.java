package springbootvinylecommercebackend.rest;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.dto.request.OrderRequest;
import springbootvinylecommercebackend.model.Order;
import springbootvinylecommercebackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderAPI {

	private final OrderService orderService;
	
	@GetMapping("")
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
		HashMap<String, Object> result = new HashMap<>();

		try {
			orderService.saveOrder(orderRequest);
//			orderService.sendOrderSuccessMail(order.getEmail());
			result.put("success", true);
			result.put("message", "Success to call API place order");
			result.put("data", orderRequest);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API place order");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}
	
	
}
