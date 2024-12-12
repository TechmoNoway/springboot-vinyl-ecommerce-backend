package springbootvinylecommercebackend.service;

import java.util.List;
import java.util.Optional;

import springbootvinylecommercebackend.model.User;

public interface UserService {
	List<User> getAllUsers();

	void updateUserInfo(User user);

	Optional<User> getUserByUsername(String username);
}
