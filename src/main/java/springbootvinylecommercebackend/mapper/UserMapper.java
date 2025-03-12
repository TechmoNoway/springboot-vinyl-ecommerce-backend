package springbootvinylecommercebackend.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.User;

@Mapper
public interface UserMapper {
	
	List<User> getAllUsers();

	Boolean existsByEmail(@Param("email") String email);

	void saveUser(User user);
	
	void updateUserInfo(User user);
	
	Optional<User> getUserByEmail(@Param("email") String email);

	User getUserById(@Param("id") Long id);

	void changePassword(@Param("userID") Long userID, @Param("newPassword") String newPassword);
}
