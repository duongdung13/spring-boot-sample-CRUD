package com.example.api.demo.spring.boot.repository;

import com.example.api.demo.spring.boot.entity.User;
import com.example.api.demo.spring.boot.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
