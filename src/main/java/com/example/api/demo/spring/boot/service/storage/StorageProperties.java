package com.example.api.demo.spring.boot.service.storage;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@ConfigurationProperties("storage")
@Component
public class StorageProperties {
    private String location = "upload-dir";

    public void setLocation(String location) {
        this.location = location;
    }
}
