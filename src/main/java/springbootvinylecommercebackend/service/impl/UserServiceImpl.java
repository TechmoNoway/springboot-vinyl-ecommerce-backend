package springbootvinylecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import springbootvinylecommercebackend.dto.request.ChangeUserPasswordRequest;
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
        userMapper.updateUserInfo(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userMapper.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public void changePassword(ChangeUserPasswordRequest request) {
        User user = userMapper.getUserById(request.getUserID());

        if (passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())) {
            if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
                System.out.println(request.getUserID() + " " + request.getNewPassword());
                userMapper.changePassword(request.getUserID(), passwordEncoder.encode(request.getNewPassword()));
            }
        }
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void deleteUser(Long id) {

    }
}
