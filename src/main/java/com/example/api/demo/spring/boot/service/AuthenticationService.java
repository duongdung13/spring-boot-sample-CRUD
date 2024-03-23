package com.example.api.demo.spring.boot.service;

import com.example.api.demo.spring.boot.dto.request.AuthenticationRequest;
import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.exception.AppException;
import com.example.api.demo.spring.boot.exception.ErrorCode;
import com.example.api.demo.spring.boot.mapper.UserMapper;
import com.example.api.demo.spring.boot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    UserMapper userMapper;

    public boolean authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = this.userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = userOptional.get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

}
