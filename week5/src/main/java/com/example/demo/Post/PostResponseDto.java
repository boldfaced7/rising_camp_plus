package com.example.demo.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }
}
