package com.example.demo.comment;

import com.example.demo.BaseTimeEntity;
import com.example.demo.post.Post;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @ToString.Exclude
    @JoinColumn(name = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }
}