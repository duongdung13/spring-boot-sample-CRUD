package com.example.api.demo.spring.boot.mapper;

import com.example.api.demo.spring.boot.dto.request.PostCreateRequest;
import com.example.api.demo.spring.boot.dto.response.PostResponse;
import com.example.api.demo.spring.boot.entity.Post;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostCreateRequest request);

    PostResponse toPostResponse(Post post);

}
