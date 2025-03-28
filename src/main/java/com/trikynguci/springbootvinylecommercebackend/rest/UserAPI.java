package com.trikynguci.springbootvinylecommercebackend.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trikynguci.springbootvinylecommercebackend.dto.request.ChangeUserPasswordRequest;
import com.trikynguci.springbootvinylecommercebackend.model.User;
import com.trikynguci.springbootvinylecommercebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserAPI {

	private final UserService userService;

	@GetMapping("/")
	public ResponseEntity<?> doGetAllUsers() {
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
	
	@GetMapping("/email")
	public ResponseEntity<?> doGetUserByEmail(@RequestParam("address") String address) {
		HashMap<String, Object> result = new HashMap<>();
		try {
			Optional<User> data = userService.getUserByEmail(address);
			result.put("success", true);
			result.put("message", "Success to call API get user by email");
			result.put("data", data);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API get user by email");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doGetUserById(@PathVariable("id") Long id) {
		HashMap<String, Object> result = new HashMap<>();
		try {
			result.put("success", true);
			result.put("message", "Success to call API getUserById");
			result.put("data", userService.getUserById(id));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API getUserById");
			result.put("data", null);
		}

		return ResponseEntity.ok(result);
	}

	@PutMapping("/update")
	public ResponseEntity<?> doUpdateUserInfo(@RequestBody User user) {
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

	@PutMapping("/change-password")
	public ResponseEntity<?> doChangePassword(@RequestBody ChangeUserPasswordRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		try {
			userService.changePassword(request);
			result.put("success", true);
			result.put("message", "Success to call API Change Password");
			result.put("data", request.getUserID());
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "Fail to call API Change Password");
			result.put("data", null);
			log.error("Error: ", e);
		}

		return ResponseEntity.ok(result);
	}


}
