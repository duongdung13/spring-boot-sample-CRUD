package com.example.api.demo.spring.boot.controller;

import com.example.api.demo.spring.boot.dto.request.ApiResponse;
import com.example.api.demo.spring.boot.dto.request.AuthenticationRequest;
import com.example.api.demo.spring.boot.dto.request.IntrospectRequest;
import com.example.api.demo.spring.boot.dto.response.AuthenticationResponse;
import com.example.api.demo.spring.boot.dto.response.IntrospectResponse;
import com.example.api.demo.spring.boot.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse rs = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder().result(rs).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspectToken(@RequestBody @Valid IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse rs = authenticationService.introspectToken(request);

        return ApiResponse.<IntrospectResponse>builder().result(rs).build();
    }

}
