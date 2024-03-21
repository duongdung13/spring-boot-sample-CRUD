package com.example.api.demo.spring.boot.controller;

import com.example.api.demo.spring.boot.dto.ApiResponse;
import com.example.api.demo.spring.boot.dto.UserLoginRequest;
import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    ApiResponse<User> loginUser(@RequestBody @Valid UserLoginRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.loginUser(request));

        return apiResponse;
    }

}
