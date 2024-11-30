package springbootvinylecommercebackend.service;

import java.util.List;

import springbootvinylecommercebackend.model.User;

public interface UserService {
	
	List<User> getAllUsers();
	
	User checkLogin(String username, String password);
		
	void saveUserRegister(User userParam);
	
	void updateUserInfo(User user);
	
	User getUserByUsername(String username);
}
