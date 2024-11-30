	package springbootvinylecommercebackend.restcontroller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.service.DiscService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/disc")
public class DiscAPI {

	@Autowired
	private DiscService discService;

	@GetMapping("/getAllDisc")
	ResponseEntity<?> doGetAllDisc() {

		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getAllDisc");
			result.put("data", discService.getAllDisc());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getAllDisc");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getLessDiscByName")
	ResponseEntity<?> doGetLessDiscByName(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getLessDiscByName");
			result.put("data", discService.getLessDiscByName(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getLessDiscByName");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);

	}
	
	@GetMapping("/getBestDiscs")
	ResponseEntity<?> doGetBestDiscs() {

		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API get Best Discs");
			result.put("data", discService.getBestDiscs());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API get Best Discs");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getMoreDiscByName")
	ResponseEntity<?> doGetMoreDiscByName(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("message", "Success to call API getMoreDiscByName");
			result.put("data", discService.getMoreDiscByName(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getMoreDiscByName");
			result.put("data", null);
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);

	}
	
	@GetMapping("/getDiscByNameASC")
	ResponseEntity<?> doGetDiscByNameASC(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();
		
		try {
			result.put("success", true);
			result.put("message", "Success to call API getDiscByNameASC");
			result.put("data", discService.getDiscByNameASC(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getDiscByNameASC");
			result.put("data", null);
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getDiscByNameDESC")
	ResponseEntity<?> doGetDiscByNameDESC(@RequestParam("searchParam") String searchParam) {
		HashMap<String, Object> result = new HashMap<>();
		
		try {
			result.put("success", true);
			result.put("message", "Success to call API getDiscByNameDESC");
			result.put("data", discService.getDiscByNameDESC(searchParam));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getDiscByNameDESC");
			result.put("data", null);
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getDiscByNameFiltered")
	ResponseEntity<?> doGetDiscByNameFiltered (@RequestParam("searchParam") String searchParam, @RequestParam("categoryName")String categoryName, @RequestParam("moodName") String moodName, @RequestParam("releaseYear") String releaseYear, @RequestParam("stockStatus") String stockStatus){
		HashMap<String, Object> result = new HashMap<>();
		
		try {
			result.put("success", true);
			result.put("message", "Success to call API getDiscByNameFiltered");
			result.put("data", discService.getDiscByNameFiltered(searchParam, categoryName, moodName, releaseYear, stockStatus));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getDiscByNameFiltered");
			result.put("data", null);
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}

}
