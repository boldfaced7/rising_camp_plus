package com.example.demo.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String content;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }
}
