package com.example.api.demo.spring.boot.controller;

import com.example.api.demo.spring.boot.dto.ApiResponse;
import com.example.api.demo.spring.boot.dto.UserLoginRequest;
import com.example.api.demo.spring.boot.dto.response.AuthenticationResponse;
import com.example.api.demo.spring.boot.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid UserLoginRequest request) {
        boolean rs = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(AuthenticationResponse.builder().authenticated(rs).build()).build();
    }

}
