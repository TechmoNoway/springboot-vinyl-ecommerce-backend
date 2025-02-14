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

	@GetMapping("/getAllProducts")
	ResponseEntity<?> doGetAllProduct() {
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getAllProducts");
			result.put("data", ProductService.getAllProduct());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getAllProducts");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getReadyProducts")
	ResponseEntity<?> doGetReadyProducts() {
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

	@GetMapping("/getProductByTitle")
	ResponseEntity<?> doGetProductByTitle(@RequestParam("title") String title) {
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

	@GetMapping("/searchProductsByTitle")
	ResponseEntity<?> doSearchProductsByTitle(@RequestParam("title") String title) {
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

	@GetMapping("/getProductByNameFiltered")
	ResponseEntity<?> doGetProductByNameFiltered (@RequestParam("searchParam") String searchParam, @RequestParam("categoryName")String categoryName, @RequestParam("moodName") String moodName, @RequestParam("releaseYear") String releaseYear, @RequestParam("stockStatus") String stockStatus){
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getProductByNameFiltered");
			result.put("data", ProductService.getProductByNameFiltered(searchParam, categoryName, moodName, releaseYear, stockStatus));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getProductByNameFiltered");
			result.put("data", null);
			log.error("Error", e);
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getMoreProductByName")
	ResponseEntity<?> doGetMoreProductByName(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getMoreProductByName");
			result.put("data", ProductService.getMoreProductByName(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getMoreProductByName");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getProductByNameASC")
	ResponseEntity<?> doGetProductByNameASC(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();
		
		try {
			result.put("success", true);
			result.put("message", "Success to call API getProductByNameASC");
			result.put("data", ProductService.getProductByNameASC(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getProductByNameASC");
			result.put("data", null);
			log.error("Error", e);
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getProductByNameDESC")
	ResponseEntity<?> doGetProductByNameDESC(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();
		
		try {
			result.put("success", true);
			result.put("message", "Success to call API getProductByNameDESC");
			result.put("data", ProductService.getProductByNameDESC(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getProductByNameDESC");
			result.put("data", null);
			log.error("Error", e);
		}
		
		return ResponseEntity.ok(result);
	}
	

}
