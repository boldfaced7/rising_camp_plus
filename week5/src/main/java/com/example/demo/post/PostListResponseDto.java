package com.example.demo.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String content;
    private String location;
    private String userNickname;
    private Long userId;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.location = post.getLocation();
        this.userNickname = post.getUser().getNickname();
        this.userId = post.getUser().getId();
    }
}
