package com.example.api.demo.spring.boot.service;

import com.example.api.demo.spring.boot.dto.UserLoginRequest;
import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.exception.AppException;
import com.example.api.demo.spring.boot.exception.ErrorCode;
import com.example.api.demo.spring.boot.mapper.UserMapper;
import com.example.api.demo.spring.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User loginUser(UserLoginRequest request) {
        if (!this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = this.userRepository.findByUsername(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        String pwEncode = passwordEncoder.encode(request.getPassword());

        if( user.getPassword().equals(pwEncode) ){
            return user;
        }

        return null;
    }

}
