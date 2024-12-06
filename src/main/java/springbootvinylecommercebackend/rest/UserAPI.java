package springbootvinylecommercebackend.rest;

import java.util.HashMap;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserAPI {


	private final UserService userService;

	@PostMapping("/login")
	ResponseEntity<?> doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {

		HashMap<String, Object> result = new HashMap<>();

		try {
			User data = userService.login(username, password);
			result.put("success", true);
			result.put("message", "Success to call API checkLogin");
			result.put("data", data);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API checkLogin");
			result.put("data", null);
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping("/register")
	ResponseEntity<?> doRegister(@RequestBody User userParam) {

		HashMap<String, Object> result = new HashMap<>();

		try {
			userService.saveUserRegister(userParam);
			result.put("success", true);
			result.put("message", "Success to call API doRegister");
			result.put("data", userParam);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API doRegister");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);

	}

	@GetMapping("/getAllUsers")
	ResponseEntity<?> doGetAllUsers() {

		HashMap<String, Object> result = new HashMap<>();

		try {
			List<User> data = userService.getAllUsers();
			result.put("success", true);
			result.put("message", "Success to call API doGetAllUsers");
			result.put("data", data);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API doGetAllUsers");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);

	}
	
	@GetMapping("/getUserByUsername")
	ResponseEntity<?> doGetUserByUserName(@RequestParam("username") String username) {

		HashMap<String, Object> result = new HashMap<>();

		try {
			User data = userService.getUserByUsername(username);
			result.put("success", true);
			result.put("message", "Success to call API getUserByUsername");
			result.put("data", data);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getUserByUsername");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);

	}

	@PutMapping("/updateUserInfo")
	ResponseEntity<?> doUpdateUserInfo(@RequestBody User user) {
		HashMap<String, Object> result = new HashMap<>();

		try {
			userService.updateUserInfo(user);
			result.put("success", true);
			result.put("message", "Success to call API Update User Info");
			result.put("data", user);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API Update User Info");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}

}
