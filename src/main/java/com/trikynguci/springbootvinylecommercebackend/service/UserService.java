package com.trikynguci.springbootvinylecommercebackend.service;

import java.util.List;
import java.util.Optional;

import com.trikynguci.springbootvinylecommercebackend.dto.request.ChangeUserPasswordRequest;
import com.trikynguci.springbootvinylecommercebackend.model.User;

public interface UserService {
	List<User> getAllUsers();

	void updateUserProfile(User user);

	Optional<User> getUserByEmail(String email);

	void changePassword(ChangeUserPasswordRequest request);

	User getUserById(Long id);

	void saveUser(User user);

	void deleteUser(Long id);
}
