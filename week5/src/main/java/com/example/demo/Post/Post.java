package com.example.demo.Post;

import com.example.demo.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Builder
    public Post(String content) {
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
