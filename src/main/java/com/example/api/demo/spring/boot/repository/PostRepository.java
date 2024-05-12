package com.example.api.demo.spring.boot.repository;

import com.example.api.demo.spring.boot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    boolean existsByTitle(String title);
}
