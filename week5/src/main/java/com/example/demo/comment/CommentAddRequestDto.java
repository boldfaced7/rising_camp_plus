package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentAddRequestDto {
    private String content;

    @Builder
    public CommentAddRequestDto(String content) {
        this.content = content;
    }

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }
}
