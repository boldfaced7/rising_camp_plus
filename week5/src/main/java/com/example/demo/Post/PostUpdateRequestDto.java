package com.example.demo.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

    private String content;

    @Builder
    public PostUpdateRequestDto(String content) {
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .content(content)
                .build();
    }
}
