package com.example.api.demo.spring.boot.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 3, message = "INVALID_PASSWORD")
    String password;
}
