package com.example.api.demo.spring.boot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "posts")
public class Post {
    public enum EnumStatus {
        ACTIVE,
        INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    String id;

    String title;

    String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatus status;
}
