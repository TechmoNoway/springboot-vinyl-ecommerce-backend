package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.User;

@Mapper
public interface UserMapper {
	
	List<User> getAllUsers();
	
	User checkLogin(@Param("username") String username, @Param("password") String password);

	void saveUserRegister(User userParam);	
	
	void updateUserInfo(User user);
	
	User getUserByUsername(@Param("username")String username);
}
