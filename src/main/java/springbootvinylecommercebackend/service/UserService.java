package springbootvinylecommercebackend.service;

import java.util.List;
import java.util.Optional;

import springbootvinylecommercebackend.model.User;

public interface UserService {
	List<User> getAllUsers();

	User login(String username, String password);

	void saveUserRegister(User userParam);

	void updateUserInfo(User user);

	Optional<User> getUserByUsername(String username);
}
