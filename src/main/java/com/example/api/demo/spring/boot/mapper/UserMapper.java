package com.example.api.demo.spring.boot.mapper;

import com.example.api.demo.spring.boot.dto.request.UserCreateRequest;
import com.example.api.demo.spring.boot.dto.request.UserUpdateRequest;
import com.example.api.demo.spring.boot.dto.response.UserResponse;
import com.example.api.demo.spring.boot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
