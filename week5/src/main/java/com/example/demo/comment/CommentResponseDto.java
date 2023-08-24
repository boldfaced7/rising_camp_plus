package com.example.demo.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String userNickname;
    private Long userId;
    private LocalDateTime createdDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userNickname = comment.getUser().getNickname();
        this.userId = comment.getUser().getId();
        this.createdDate = comment.getCreatedDate();
    }
}
