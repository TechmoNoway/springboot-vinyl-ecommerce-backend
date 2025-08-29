package com.trikynguci.springbootvinylecommercebackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.trikynguci.springbootvinylecommercebackend.dto.request.ChangeUserPasswordRequest;
import com.trikynguci.springbootvinylecommercebackend.mapper.UserMapper;
import com.trikynguci.springbootvinylecommercebackend.model.User;
import com.trikynguci.springbootvinylecommercebackend.dto.request.UpdateUserProfileRequest;
import com.trikynguci.springbootvinylecommercebackend.service.UserService;

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
    public void updateUserProfile(UpdateUserProfileRequest request) {
        User user = userMapper.getUserById(request.getId());
        if (user == null) throw new IllegalArgumentException("User not found");
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getFullname() != null) user.setFullname(request.getFullname());
        if (request.getBirthday() != null) user.setBirthday(request.getBirthday());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
        userMapper.updateUserProfile(user);
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
        }else {
            throw new IllegalArgumentException("Current password is incorrect");
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
