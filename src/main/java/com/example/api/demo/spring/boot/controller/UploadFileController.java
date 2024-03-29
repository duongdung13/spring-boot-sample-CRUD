package com.example.api.demo.spring.boot.controller;

import com.example.api.demo.spring.boot.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload-file")
public class UploadFileController {

    private final StorageService storageService;

    @Autowired
    public UploadFileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);

        return "Upload Successful";
    }
}
