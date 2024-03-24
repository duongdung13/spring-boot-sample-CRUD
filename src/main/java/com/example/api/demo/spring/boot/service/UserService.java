package com.example.api.demo.spring.boot.service;

import com.example.api.demo.spring.boot.dto.request.ApiResponse;
import com.example.api.demo.spring.boot.dto.request.UserCreateRequest;
import com.example.api.demo.spring.boot.dto.request.UserUpdateRequest;
import com.example.api.demo.spring.boot.dto.response.UserResponse;
import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.enums.Role;
import com.example.api.demo.spring.boot.exception.AppException;
import com.example.api.demo.spring.boot.exception.ErrorCode;
import com.example.api.demo.spring.boot.mapper.UserMapper;
import com.example.api.demo.spring.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreateRequest request) {
        if (this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<UserResponse> getUsers() {
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("getName " + userAuth.getName());
        userAuth.getAuthorities().forEach(grantedAuthority -> {
            System.out.println("grantedAuthority: " + grantedAuthority.getAuthority());
        });

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
