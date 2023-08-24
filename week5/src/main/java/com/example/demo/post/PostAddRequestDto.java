package com.example.demo.post;

import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostAddRequestDto {

    private String content;
    private String location;

    @Builder
    public PostAddRequestDto(String content, String location) {
        this.content = content;
        this.location = location;
    }

    public Post toEntity(User user) {
        return Post.builder()
                .content(content)
                .location(location)
                .user(user)
                .build();
    }
}
