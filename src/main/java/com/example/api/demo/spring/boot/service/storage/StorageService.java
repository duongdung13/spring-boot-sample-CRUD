package com.example.api.demo.spring.boot.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void store(MultipartFile file);
}
