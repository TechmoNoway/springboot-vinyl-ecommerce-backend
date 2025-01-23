package springbootvinylecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import springbootvinylecommercebackend.mapper.UserMapper;
import springbootvinylecommercebackend.model.User;
import springbootvinylecommercebackend.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public void updateUserInfo(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.updateUser(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userMapper.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
