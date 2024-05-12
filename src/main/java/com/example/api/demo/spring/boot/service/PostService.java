package com.example.api.demo.spring.boot.service;

import com.example.api.demo.spring.boot.dto.request.PostCreateRequest;
import com.example.api.demo.spring.boot.dto.response.PostResponse;
import com.example.api.demo.spring.boot.entity.Post;
import com.example.api.demo.spring.boot.exception.AppException;
import com.example.api.demo.spring.boot.exception.ErrorCode;
import com.example.api.demo.spring.boot.mapper.PostMapper;
import com.example.api.demo.spring.boot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;


    public PostResponse createPost(PostCreateRequest request) {
        if (this.postRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.POST_EXISTED);
        }

        Post.EnumStatus status = Post.EnumStatus.valueOf(request.getStatus());

        Post post = postMapper.toPost(request);

        return postMapper.toPostResponse(postRepository.save(post));
    }
}
