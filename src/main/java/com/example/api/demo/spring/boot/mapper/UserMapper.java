package com.example.api.demo.spring.boot.mapper;

import com.example.api.demo.spring.boot.dto.UserCreateRequest;
import com.example.api.demo.spring.boot.dto.UserUpdateRequest;
import com.example.api.demo.spring.boot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
