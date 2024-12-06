package springbootvinylecommercebackend.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.User;

@Mapper
public interface UserMapper {
	
	List<User> getAllUsers();
	
	User checkLogin(@Param("username") String username, @Param("password") String password);

	void saveUserRegister(User userParam);	
	
	void updateUserInfo(User user);
	
	Optional<User> getUserByUsername(@Param("username")String username);
}
