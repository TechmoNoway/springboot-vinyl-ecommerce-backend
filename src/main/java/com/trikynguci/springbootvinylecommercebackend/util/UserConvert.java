package com.trikynguci.springbootvinylecommercebackend.util;

import org.springframework.stereotype.Component;
import com.trikynguci.springbootvinylecommercebackend.dto.request.UserRequest;
import com.trikynguci.springbootvinylecommercebackend.dto.response.UserResponse;
import com.trikynguci.springbootvinylecommercebackend.model.User;

@Component
public class UserConvert {
    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .phone(user.getPhone())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .roleId(user.getRoleId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User toUser(UserRequest UserRequest) {
        return User.builder()
                .id(UserRequest.getId())
                .email(UserRequest.getEmail())
                .fullname(UserRequest.getFullname())
                .phone(UserRequest.getPhone())
                .gender(UserRequest.getGender())
                .birthday(UserRequest.getBirthday())
                .address(UserRequest.getAddress())
                .build();
    }
}
