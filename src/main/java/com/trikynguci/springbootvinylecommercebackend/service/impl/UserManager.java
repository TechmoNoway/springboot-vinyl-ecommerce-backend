package com.trikynguci.springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.trikynguci.springbootvinylecommercebackend.mapper.UserMapper;
import com.trikynguci.springbootvinylecommercebackend.model.User;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserManager implements UserDetailsManager {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.saveUser((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("Email {0} not found", email)));
    }
}
