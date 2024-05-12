package com.example.api.demo.spring.boot.controller;

import com.example.api.demo.spring.boot.dto.request.ApiResponse;
import com.example.api.demo.spring.boot.dto.request.PostCreateRequest;
import com.example.api.demo.spring.boot.dto.response.PostResponse;
import com.example.api.demo.spring.boot.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ApiResponse<PostResponse> createUser(@RequestBody @Valid PostCreateRequest request) {
        ApiResponse<PostResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(postService.createPost(request));

        return apiResponse;
    }

}
