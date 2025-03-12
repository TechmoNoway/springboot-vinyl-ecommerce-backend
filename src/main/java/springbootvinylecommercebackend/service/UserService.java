package springbootvinylecommercebackend.service;

import java.util.List;
import java.util.Optional;

import springbootvinylecommercebackend.dto.request.ChangeUserPasswordRequest;
import springbootvinylecommercebackend.model.User;

public interface UserService {
	List<User> getAllUsers();

	void updateUserInfo(User user);

	Optional<User> getUserByEmail(String email);

	void changePassword(ChangeUserPasswordRequest request);

	User getUserById(Long id);

	void saveUser(User user);

	void deleteUser(Long id);
}
