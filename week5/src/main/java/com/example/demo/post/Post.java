package com.example.demo.post;

import com.example.demo.BaseTimeEntity;
import com.example.demo.comment.Comment;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String location;

    @ToString.Exclude
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String content, String location, User user) {
        this.content = content;
        this.location = location;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }
}
